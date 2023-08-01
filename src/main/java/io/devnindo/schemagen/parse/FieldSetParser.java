/*
 * Copyright 2023 devnindo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.devnindo.schemagen.parse;

import com.thoughtworks.qdox.model.*;
import com.thoughtworks.qdox.model.impl.DefaultJavaParameterizedType;
import io.devnindo.datatype.schema.AField;
import io.devnindo.datatype.schema.IgnoreField;
import io.devnindo.datatype.schema.Required;
import io.devnindo.schemagen.spec.FieldTypeSpec;
import io.devnindo.schemagen.spec.SchemaFieldSpec;
import io.devnindo.schemagen.utils.CaseUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public class FieldSetParser {
    private static final String[] GETTER_PREFIX_LIST = {"get", "is", "has", "should", "can"};
    private static final String LIST_TYPE = "java.util.List";
    public final JavaClass javaClz;
    private final BeanChecker beanChecker;

    public FieldSetParser(JavaClass javaClz, BeanChecker beanChecker$) {
        this.javaClz = javaClz;
        beanChecker = beanChecker$;
    }

    public Set<SchemaFieldSpec> buildFieldSpecSet() {

        List<JavaField> clzFieldList = javaClz.getFields();
        List<JavaField> dataFieldList = clzFieldList
                .stream()
                .filter(field -> !field.isPublic() && !hasAnnotation0(field.getAnnotations(), IgnoreField.class))
                .collect(Collectors.toList());
        //  .collect(Collectors.toMap(field -> field.getName(), field -> field));

        if (dataFieldList.size() < clzFieldList.size()) {
            int nonD = clzFieldList.size() - dataFieldList.size();
            System.out.printf("#WARNING: %s bean has %d non-data fields." +
                    "\n\t\t A data field must be either protected or package-private\n", javaClz.getFullyQualifiedName(), nonD);
        }


        Map<String, JavaMethod> methodNameMap = javaClz.getMethods()
                .stream()
                .filter(m$ -> m$.isPublic())
                .collect(Collectors.toMap(m$ -> m$.getName(), m$ -> m$));


        Set<SchemaFieldSpec> fieldSpecSet = new HashSet<>();
        for (JavaField field : dataFieldList) {
            SchemaFieldSpec fieldSpec = createSpec(field, methodNameMap);
            fieldSpecSet.add(fieldSpec);
        }

        fieldSpecSet.addAll(buildReadOnlyFieldSpecSet0());

        return fieldSpecSet;
    }

    private SchemaFieldSpec createSpec(JavaField field$, Map<String, JavaMethod> methodMap$) {
        String fieldName = field$.getName();
        JavaClass fieldType = field$.getType();
        boolean required = this.hasAnnotation0(field$.getAnnotations(), Required.class);
        String getterName = calcValidGetter0(field$.getName(), field$.getType(), methodMap$);
        String setterName = calcValidSetter0(fieldName, fieldType, methodMap$);
        FieldTypeSpec typeSpec = calcFieldTypeSpec0(fieldName, fieldType);

        return new SchemaFieldSpec.Builder().setName(fieldName)
                .setGetterFunc(getterName)
                .setSetterFunc(setterName)
                .setReadOnly(false)
                .setRequired(required)
                .setTypeSpec(typeSpec)
                .build();
    }

    private Set<SchemaFieldSpec> buildReadOnlyFieldSpecSet0() {
        return javaClz.getMethods().stream()
                .filter(m$ -> hasAnnotation0(m$.getAnnotations(), AField.class)
                        && CaseUtil.matchPrefix(m$.getName(), GETTER_PREFIX_LIST).isRight()
                        && m$.isPublic()
                )
                .map(m$ -> {
                    String pascalName = CaseUtil.ifPrefixedThanSuffix(m$.getName(), GETTER_PREFIX_LIST).right();
                    String fieldName = CaseUtil.pascalToCamel(pascalName);
                    FieldTypeSpec typeSpec = calcFieldTypeSpec0(fieldName, m$.getReturns());
                    return new SchemaFieldSpec.Builder()
                            .setName(fieldName)
                            .setGetterFunc(m$.getName())
                            .setTypeSpec(typeSpec)
                            .setReadOnly(true)
                            .build();
                })
                .collect(Collectors.toSet());
    }

    private String calcValidGetter0(String fieldName$, JavaClass fieldType$, Map<String, JavaMethod> methodMap$) {
        JavaMethod getter = null;
        String pascalCase = CaseUtil.camelToPascal(fieldName$);
        String typeName = fieldType$.getFullyQualifiedName();
        for (String prefix : GETTER_PREFIX_LIST) {
            String methodName = prefix + pascalCase;
            if (methodMap$.containsKey(methodName)) {
                getter = methodMap$.get(methodName);
            }
        }
        String notNullGetterMSG = initClzFieldInfo("Missing Getter func for a data field", fieldName$, typeName).toString();
        Objects.requireNonNull(getter, notNullGetterMSG);

        JavaClass getterReturnType = getter.getReturns();
        if (getterReturnType.isA(fieldType$) == false) {
            String invalidGetterMsg = initClzFieldInfo("Invalid Getter Return Type", fieldName$, typeName)
                    .toString();
            throw new RuntimeException(invalidGetterMsg);
        }

        return getter.getName();

    }

    private String calcValidSetter0(String fieldName$, JavaClass fieldType$, Map<String, JavaMethod> methodMap$) {
        String pascalCase = CaseUtil.camelToPascal(fieldName$);
        JavaMethod setter = methodMap$.get("set" + pascalCase);
        if (setter == null)
            return null;
        JavaClass setterArgType = setter.getParameters().get(0).getJavaClass();
        if (setterArgType.isA(fieldType$) == false) {
            String typeName = fieldType$.getFullyQualifiedName();
            String setterTypeMismatchMsg = initClzFieldInfo("Invalid Setter Argument", fieldName$, typeName).toString();
            throw new RuntimeException(setterTypeMismatchMsg);
        }
        return setter.getName();
    }


    private FieldTypeSpec calcFieldTypeSpec0(String fieldName, JavaClass fieldType) {
        String typeName = fieldType.getFullyQualifiedName();
        FieldTypeSpec fieldTypeSpec = null;
        if (beanChecker.isDataBean(fieldType)) {
            fieldTypeSpec = FieldTypeSpec.init(typeName, FieldTypeSpec.TypeGroup.BEAN);
        } else if (typeName.equals(LIST_TYPE)) {

            DefaultJavaParameterizedType genericType = (DefaultJavaParameterizedType) fieldType;
            JavaClass paramType = (JavaClass) genericType.getActualTypeArguments().get(0);

            String paramTypeName = paramType.getFullyQualifiedName();
            if (FieldTypeSpec.isInPlainSet(paramTypeName))
                fieldTypeSpec = FieldTypeSpec.init(paramTypeName, FieldTypeSpec.TypeGroup.PLAIN_LIST);
            else if (beanChecker.isDataBean(paramType))
                fieldTypeSpec = FieldTypeSpec.init(paramTypeName, FieldTypeSpec.TypeGroup.BEAN_LIST);

            else throw initInvalidTypeException(fieldName, "List<" + paramTypeName + ">");
        } else if (fieldType.isEnum())
            fieldTypeSpec = FieldTypeSpec.init(typeName, FieldTypeSpec.TypeGroup.AN_ENUM);
        else if (FieldTypeSpec.isInPlainSet(typeName))
            fieldTypeSpec = FieldTypeSpec.init(typeName, FieldTypeSpec.TypeGroup.PLAIN);

        else {

            throw initInvalidTypeException(fieldName, typeName);
        }

        return fieldTypeSpec;
    }

    private boolean hasAnnotation0(List<JavaAnnotation> annotationList, Class<? extends Annotation> annot$) {
        for (JavaAnnotation annotation : annotationList) {
            String fullName = annotation.getType().getFullyQualifiedName();
            if (annot$.getName().equals(fullName))
                return true;
        }
        return false;

    }

    private RuntimeException initInvalidTypeException(String fieldName, String typeName) {
        String info = initClzFieldInfo("Declared Type doesn't conform to valid Json", fieldName, typeName)
                .append("
                        ")
                                .toString();
        return new RuntimeException(info);
    }

    private StringBuilder initClzFieldInfo(String message, String fieldName, String typeName$) {
        return new StringBuilder()
                .append(message)
                .append("
                        ").append("BEAN"+javaClz.getFullyQualifiedName())
                                .append("
                                        ").append("FIELD"+fieldName)
                                                .append("
                                                        ").append("TYPE"+typeName$);
    }

}

