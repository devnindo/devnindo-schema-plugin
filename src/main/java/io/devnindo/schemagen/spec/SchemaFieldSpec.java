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

import io.devnindo.schemagen.utils.CaseUtil;

import java.util.Objects;

public class SchemaFieldSpec {
    public final String name;
    public final String nameSnake;
    public final String nameSnakeUpper;
    public final String getterFunc;//getField, hasField,  isField, canField, shouldField
    public final String setterFunc;
    public final FieldTypeSpec typeSpec;
    public final boolean readOnly;
    public final boolean required;

    public SchemaFieldSpec(String name, String getterFunc, String setterFunc, FieldTypeSpec typeSpec, Boolean readOnly, Boolean required) {
        this.name = name;
        this.nameSnake = CaseUtil.toSnake(name);
        this.nameSnakeUpper = nameSnake.toUpperCase();
        this.getterFunc = getterFunc;
        this.setterFunc = setterFunc;
        this.typeSpec = typeSpec;
        this.readOnly = readOnly;
        this.required = required;
    }

    public boolean hasSetter() {
        return Objects.nonNull(setterFunc);
    }

    @Override
    public String toString() {
        return "FIELD {" +
                "name='" + name + '\'' +
                ", getterFunc='" + getterFunc + '\'' +
                ", setterFunc='" + setterFunc + '\'' +
                ", typeSpec=" + typeSpec +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchemaFieldSpec fieldSpec = (SchemaFieldSpec) o;
        return name.equals(fieldSpec.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static final class Builder {
        private String name;
        private String getterFunc;//getField, hasField,  isField, canField, shouldField
        private String setterFunc;
        private FieldTypeSpec typeSpec;
        private boolean readOnly;
        private boolean required;


        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public Builder setGetterFunc(String getterFunc) {
            this.getterFunc = getterFunc;
            return this;
        }

        public Builder setSetterFunc(String setterFunc) {
            this.setterFunc = setterFunc;
            return this;
        }

        public Builder setTypeSpec(FieldTypeSpec typeSpec) {
            this.typeSpec = typeSpec;
            return this;
        }

        public Builder setReadOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder setRequired(boolean required) {
            this.required = required;
            return this;
        }

        public SchemaFieldSpec build() {
            return new SchemaFieldSpec(name, getterFunc, setterFunc, typeSpec, readOnly, required);
        }
    }
}

