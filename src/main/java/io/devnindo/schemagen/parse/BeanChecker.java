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

import com.thoughtworks.qdox.model.JavaClass;

import java.util.HashSet;
import java.util.Set;

public final class BeanChecker {
    private static final String DATA_BEAN_IF = "io.devnindo.datatype.schema.DataBean";
    Set<String> beanClzSet;

    BeanChecker() {
        beanClzSet = new HashSet<>();
    }

    public boolean isDataBean(JavaClass clz$) {
        if (beanClzSet.contains(clz$.getFullyQualifiedName()))
            return true;
        else {
            boolean aBean = isDataBean0(clz$);
            if (aBean)
                beanClzSet.add(clz$.getFullyQualifiedName());

            return aBean;
        }
    }

    private boolean isDataBean0(JavaClass clz$) {

        if (clz$.isInterface() || clz$.isAbstract() || clz$.isAnnotation() || clz$.isEnum())
            return false;

        if ("java.lang.Object".equals(clz$.getFullyQualifiedName()))
            return false;

        for (JavaClass ifClz : clz$.getInterfaces()) {
            if (DATA_BEAN_IF.equals(ifClz.getFullyQualifiedName()))
                return true;
        }

        return isDataBean0(clz$.getSuperJavaClass());
    }

}

