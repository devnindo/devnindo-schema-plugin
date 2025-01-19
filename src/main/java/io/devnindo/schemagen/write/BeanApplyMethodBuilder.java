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
import io.devnindo.datatype.json.JsonObject;
import io.devnindo.schemagen.spec.SchemaFieldSpec;

import javax.lang.model.element.Modifier;
import java.util.Set;

public class BeanApplyMethodBuilder {
    public final ClassName beanName;
    public final Set<SchemaFieldSpec> fieldSpecSet;
    private final MethodSpec.Builder mBuilder;

    public BeanApplyMethodBuilder(ClassName beanName, Set<SchemaFieldSpec> fieldSpecSet) {
        this.beanName = beanName;
        this.fieldSpecSet = fieldSpecSet;
        mBuilder = MethodSpec.methodBuilder("apply")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ClassName.get(Override.class));
    }

    protected MethodSpec buildMethodSpec() {
        mBuilder.addParameter(beanName, "bean")
                .returns(JsonObject.class);
        mBuilder.addStatement("JsonObject js = new JsonObject()");
        fieldSpecSet.forEach(f -> {
            mBuilder.addStatement("js.put($L.name, $L.toJson(bean))", f.nameSnakeUpper, f.nameSnakeUpper);
        });

        mBuilder.addStatement("return js");

        return mBuilder.build();

    }

}

