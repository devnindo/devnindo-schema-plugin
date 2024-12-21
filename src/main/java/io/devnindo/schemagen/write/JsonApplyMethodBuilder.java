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

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import io.devnindo.datatype.json.JsonObject;
import io.devnindo.schemagen.spec.SchemaFieldSpec;

import javax.lang.model.element.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonApplyBuilder {
    public final ClassName beanName;
    public final Set<SchemaFieldSpec> fieldSpecSet;
    private final MethodSpec.Builder mBuilder;

    public JsonApplyBuilder(ClassName beanName, Set<SchemaFieldSpec> fieldSpecSet) {
        this.beanName = beanName;
        this.fieldSpecSet = fieldSpecSet.stream()
                .filter(f -> f.readOnly == false)
                .collect(Collectors.toSet());
        mBuilder = MethodSpec.methodBuilder("apply")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ClassName.get(Override.class));
    }

    protected MethodSpec buildMethodSpec() {
        String jsonArg = "data";

        ParameterizedTypeName returnType = ParameterizedTypeName.get(PoetClz.EITHER, PoetClz.VIOLATION, beanName);
        mBuilder.returns(returnType)
                .addParameter(JsonObject.class, jsonArg);

        buildExtract();
        mBuilder.addCode("\n");
        buildViolationCheck();

        // if has violation than return
        mBuilder.addCode("\n")
                .addCode("if(violation.hasRequirement()) {\n")
                .addStatement("\t return Either.left(violation)")
                .addCode("}")
        ;
        mBuilder.addCode("\n");
        buildInitialization();

        // capturing type validation


        return mBuilder.build();
    }

    private void buildExtract() {
        fieldSpecSet.forEach(f -> {
            String template = "$T<$T, $T> $LEither = $L.fromJson(data)";
            if (f.typeSpec.isPlainList() || f.typeSpec.isBeanList())
                template = "$T<$T, List<$T>> $LEither = $L.fromJson(data)";

            ClassName fieldType = ClassName.bestGuess(f.typeSpec.fullName);
            mBuilder.addStatement(template, PoetClz.EITHER, PoetClz.VIOLATION, fieldType, f.name, f.nameSnakeUpper);
        });
    }

    private void buildViolationCheck() {
        mBuilder.addStatement("$T violation = newViolation($T.class)", PoetClz.OBJECT_VIOLATION, beanName);
        fieldSpecSet.forEach(f -> {
            mBuilder.addStatement("violation.check($L, $LEither)", f.nameSnakeUpper, f.name);
        });
    }

    private void buildInitialization() {
        mBuilder.addStatement("$T bean = new $T()", beanName, beanName);
        fieldSpecSet.forEach(f -> {
            if (f.hasSetter())
                mBuilder.addStatement("bean.$L($LEither.right())", f.setterFunc, f.name);
            else
                mBuilder.addStatement("bean.$L = $LEither.right()", f.name, f.name);
        });
        mBuilder.addStatement("return Either.right(bean)");
    }
}

