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
import io.devnindo.schemagen.spec.SchemaFieldSpec;

import javax.lang.model.element.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

public class DiffMethodBuilder {
    public final ClassName beanName;
    public final Set<SchemaFieldSpec> fieldSpecSet;
    private final MethodSpec.Builder mBuilder;

    public DiffMethodBuilder(ClassName beanName, Set<SchemaFieldSpec> fieldSpecSet) {
        this.beanName = beanName;
        this.fieldSpecSet = fieldSpecSet.stream()
                .filter(f -> f.readOnly == false)
                .collect(Collectors.toSet());
        mBuilder = MethodSpec.methodBuilder("diff")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ClassName.get(Override.class));
    }

    public MethodSpec buildMethodSpec() {
        ParameterizedTypeName returnType = ParameterizedTypeName.get(PoetClz.DATA_DIFF, beanName);
        mBuilder.returns(returnType)
                .addParameter(beanName, "left")
                .addParameter(beanName, "right");

        mBuilder.addStatement("$T merged = new $T()", beanName, beanName);
        mBuilder.addStatement("JsonObject delta = new JsonObject()");
        mBuilder.addCode("\n");
        fieldSpecSet.forEach(f -> {
            mBuilder.addStatement("merged.$L = $L.diff(left, right, delta::put)", f.name, f.nameSnakeUpper);
        });
        mBuilder.addCode("\n");
        mBuilder.addStatement("return new $T<>(delta, merged)", PoetClz.DATA_DIFF);

        return mBuilder.build();
    }
}

