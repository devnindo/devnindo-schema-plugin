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
import io.devnindo.schemagen.spec.SchemaFieldSpec;
import io.devnindo.schemagen.spec.SchemaSpec;
import com.thoughtworks.qdox.JavaProjectBuilder;

import java.io.File;
import java.util.*;

public class BeanSrcTreeParser {
    private static final String DATA_BEAN_IF = "io.devnindo.datatype.schema.DataBean";
    public final File srcDir;
    BeanChecker beanChecker;

    public BeanSrcTreeParser(File srcDir$) {
        srcDir = srcDir$;
        beanChecker = new BeanChecker();
    }

    public BeanSrcTreeParser(String srcPath$) {
        this(new File(srcPath$));
    }

    public Collection<SchemaSpec> parseAndBuildSpec() {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(srcDir);
        Collection<JavaClass> clzList = builder.getClasses();


        Map<String, SchemaSpec> specMap = new HashMap<>();

        for (JavaClass clz : clzList) {
            if (beanChecker.isDataBean(clz) == false)
                continue;

            String qualifiedName = clz.getFullyQualifiedName();
            if (specMap.containsKey(qualifiedName) == false) {
                SchemaSpec spec = processClz0(clz, specMap);
                specMap.put(qualifiedName, spec);

            }
        }
        return specMap.values();
    }

    private SchemaSpec processClz0(JavaClass beanClz$, Map<String, SchemaSpec> specMap$) {
        String beanFullyQualifiedName = beanClz$.getFullyQualifiedName();
        if (specMap$.containsKey(beanFullyQualifiedName))
            return specMap$.get(beanFullyQualifiedName);

        Set<SchemaFieldSpec> fieldSpecSet = new HashSet<>();
        JavaClass superClass = beanClz$.getSuperJavaClass();
        if (beanChecker.isDataBean(superClass)) {
            SchemaSpec superSpec = processClz0(superClass, specMap$);
            fieldSpecSet.addAll(superSpec.fieldSpecSet);
        }

        FieldSetParser fieldParser = new FieldSetParser(beanClz$, beanChecker);
        fieldSpecSet.addAll(fieldParser.buildFieldSpecSet());
        SchemaSpec spec = new SchemaSpec(beanClz$.getName(), beanClz$.getPackageName(), fieldSpecSet);
        specMap$.put(beanFullyQualifiedName, spec);
        return spec;

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

