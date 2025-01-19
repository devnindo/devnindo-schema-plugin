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
package io.devnindo.schemagen.write;

import com.squareup.javapoet.*;
import io.devnindo.schemagen.spec.FieldTypeSpec;
import io.devnindo.schemagen.spec.SchemaFieldSpec;
import io.devnindo.schemagen.spec.SchemaSpec;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BeanSchemaWriter {
    public final File outDir;


    public BeanSchemaWriter(File outDir$) {

        outDir = outDir$;
    }

    public BeanSchemaWriter(String outPath$) {

        this(new File(outPath$));
    }

    public void write(Collection<SchemaSpec> specCollection)
            throws IOException {
        for (SchemaSpec spec : specCollection) {
            writeSpec0(spec);
        }
    }

    private void writeSpec0(SchemaSpec schemaSpec$)
            throws IOException {
        ClassName beanName = ClassName.get(schemaSpec$.beanPackage, schemaSpec$.beanName);

        List<FieldSpec> fieldSpecList = schemaSpec$.fieldSpecSet
                .stream()
                .map(spec$ -> this.createFieldPoem0(beanName, spec$))
                .collect(Collectors.toList());


        JsonApplyMethodBuilder jsonApply = new JsonApplyMethodBuilder(beanName, schemaSpec$.fieldSpecSet);
        MethodSpec jsonToBeanApplyMethod = jsonApply.buildMethodSpec();

        BeanApplyMethodBuilder beanApply = new BeanApplyMethodBuilder(beanName, schemaSpec$.fieldSpecSet);
        MethodSpec beanToJsonApplyMethod = beanApply.buildMethodSpec();

        DiffMethodBuilder diffBuilder = new DiffMethodBuilder(beanName, schemaSpec$.fieldSpecSet);
        MethodSpec diffMethod = diffBuilder.buildMethodSpec();
        // MethodSpec diffSpec = buildDiffSpec();

        TypeSpec schemaPoem = TypeSpec.classBuilder("$" + schemaSpec$.beanName)
                .addFields(fieldSpecList)
                .addMethod(jsonToBeanApplyMethod)
                .addMethod(beanToJsonApplyMethod)
                .addMethod(diffMethod)
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(PoetClz.BEAN_SCHEMA, beanName))
                .build();

        JavaFile javaFile = JavaFile.builder(schemaSpec$.beanPackage, schemaPoem)
                .build();
        javaFile.writeTo(outDir);

    }


    private FieldSpec createFieldPoem0(ClassName beanName$, SchemaFieldSpec spec$) {
        ParameterizedTypeName fieldTypeName;
        FieldTypeSpec typeSpec = spec$.typeSpec;
        if (typeSpec.isPlainList() || typeSpec.isBeanList()) {
            ParameterizedTypeName listType = ParameterizedTypeName.get(PoetClz.LIST, ClassName.bestGuess(typeSpec.fullName));
            fieldTypeName = ParameterizedTypeName.get(PoetClz.SCHEMA_FIELD, beanName$, listType);
        } else {
            fieldTypeName = ParameterizedTypeName.get(PoetClz.SCHEMA_FIELD, beanName$, ClassName.bestGuess(typeSpec.fullName));
        }


        ClassName beanFieldTypeName = ClassName.bestGuess(typeSpec.fullName);

        String initTemplate = "$L($S, $T::$L, $T.class, false)";
        if (spec$.required)
            initTemplate = "$L($S, $T::$L, $T.class, true)";

        return FieldSpec.builder(fieldTypeName, spec$.nameSnakeUpper)
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC, Modifier.STATIC)
                .initializer(initTemplate, typeSpec.initializerName(), spec$.nameSnake, beanName$, spec$.getterFunc, beanFieldTypeName)
                .build();

    }


}

