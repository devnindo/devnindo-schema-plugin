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
package io.devnindo.schemagen;

import io.devnindo.schemagen.parse.BeanSrcTreeParser;
import io.devnindo.schemagen.spec.SchemaSpec;
import io.devnindo.schemagen.write.BeanSchemaWriter;

import java.io.IOException;
import java.util.Collection;

public class SchemaParseTest {
    public static void main(String[] args) {

        try {
            BeanSrcTreeParser parser = new BeanSrcTreeParser("src/test/java");
            BeanSchemaWriter writer = new BeanSchemaWriter("src/test/java");

            Collection<SchemaSpec> specCollection = parser.parseAndBuildSpec();

            writer.write(specCollection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

