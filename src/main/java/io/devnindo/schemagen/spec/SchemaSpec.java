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
package io.devnindo.schemagen.spec;

import java.util.Set;

public class SchemaSpec {
    public final String beanName;  // fully qualified name
    public final String beanPackage;
    public final Set<SchemaFieldSpec> fieldSpecSet;


    public SchemaSpec(String beanName, String beanPackage, Set<SchemaFieldSpec> fieldSpecSet) {
        this.beanName = beanName;
        this.beanPackage = beanPackage;
        this.fieldSpecSet = fieldSpecSet;

    }

    @Override
    public String toString() {
        String schemaSpec = "BEAN " + beanPackage + "." + beanName;
        String fieldSpcStr = "";
        for (SchemaFieldSpec spec : fieldSpecSet) {
            fieldSpcStr += "\n " + spec.toString();
        }

        return schemaSpec + "" + fieldSpcStr;
    }
}

