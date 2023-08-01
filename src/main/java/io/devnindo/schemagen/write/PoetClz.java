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
import io.devnindo.datatype.schema.BeanSchema;
import io.devnindo.datatype.schema.DataDiff;
import io.devnindo.datatype.schema.SchemaField;
import io.devnindo.datatype.util.Either;
import io.devnindo.datatype.validation.ObjViolation;
import io.devnindo.datatype.validation.Violation;

import java.util.List;

public interface PoetClz {
    ClassName LIST = ClassName.get(List.class);
    ClassName SCHEMA_FIELD = ClassName.get(SchemaField.class);
    ClassName BEAN_SCHEMA = ClassName.get(BeanSchema.class);
    ClassName EITHER = ClassName.get(Either.class);
    ClassName VIOLATION = ClassName.get(Violation.class);
    ClassName OBJECT_VIOLATION = ClassName.get(ObjViolation.class);
    ClassName DATA_DIFF = ClassName.get(DataDiff.class);
}

