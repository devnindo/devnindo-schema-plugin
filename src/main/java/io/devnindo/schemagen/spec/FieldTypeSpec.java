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

import io.devnindo.datatype.json.JsonArray;
import io.devnindo.datatype.json.JsonObject;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FieldTypeSpec {
    public static final Set<String> PLAIN_SET = new HashSet<>(Arrays.asList(
            Integer.class.getName(),
            Long.class.getName(),
            Double.class.getName(),
            String.class.getName(),
            Boolean.class.getName(),
            Instant.class.getName(),
            JsonObject.class.getName(),
            JsonArray.class.getName()
    ));
    public final String fullName; // fully qualified name
    //public  final String paramFullName; // if LIST_OF_BEAN, BEAN, ENUM
    private TypeGroup typeGroup;

    public FieldTypeSpec(String fullName, TypeGroup typeGroup) {
        this.fullName = fullName;
        //  this.paramFullName = paramFullName;
        this.typeGroup = typeGroup;
    }

    public static final boolean isInPlainSet(String fullClzName$) {
        return PLAIN_SET.contains(fullClzName$);
    }

    public static final FieldTypeSpec init(String fullName, TypeGroup typeGroup) {
        return new FieldTypeSpec(fullName, typeGroup);
    }

    public boolean isPlain() {
        return TypeGroup.PLAIN.equals(typeGroup);
    }

    public boolean isAnEnum() {
        return TypeGroup.AN_ENUM.equals(typeGroup);
    }

    public boolean isABean() {
        return TypeGroup.BEAN.equals(typeGroup);
    }

    public boolean isPlainList() {
        return TypeGroup.PLAIN_LIST.equals(typeGroup);
    }

    public boolean isBeanList() {
        return TypeGroup.BEAN_LIST.equals(typeGroup);
    }

    public String initializerName() {
        return typeGroup.initFuncName;
    }

    @Override
    public String toString() {
        return "TYPE {" +
                "fullName='" + fullName + '\'' +
                //  ", argFullName='" + paramFullName + '\'' +
                ", typeGroup=" + typeGroup +
                '}';
    }

    public enum TypeGroup {
        PLAIN("plainField"),
        AN_ENUM("enumField"),
        PLAIN_LIST("plainListField"),
        BEAN_LIST("beanListField"),
        BEAN("beanField");

        protected String initFuncName;

        TypeGroup(String initFuncName$) {
            initFuncName = initFuncName$;
        }
    }
}
