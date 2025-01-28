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
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.Collection;
import java.util.Set;

public class SchemaGeneratorTask extends DefaultTask {


    public void generateSchema(File srcDir, File ourDir) {
        try {

            BeanSrcTreeParser parser = new BeanSrcTreeParser(srcDir);
            BeanSchemaWriter writer = new BeanSchemaWriter(ourDir);

            Collection<SchemaSpec> specCollection = parser.parseAndBuildSpec();
            writer.write(specCollection);
        } catch (Throwable th) {
            th.printStackTrace();
            System.out.println(th.getMessage());
        }
    }

    @TaskAction
    void generateSchema() {
        Project project = getProject();

        SourceSetContainer srcContainer = (SourceSetContainer) project.getProperties().get("sourceSets");
        //Set<File> srcDirs = sourceSets.getByName("main").getAllSource().getSrcDirs();


        Set<File> srcDirs = srcContainer.getByName("main").getJava().getSrcDirs();
        File outputDir = srcContainer
                .getByName("main")
                .getOutput()
                .getClassesDirs()
                .getFiles().iterator().next();

        /*
         * Add source to java poet
         * */
        //System.out.println("Total Source dirs: "+srcDirs.size());
        //System.out.println("Output directory: "+outputDir);
        srcDirs.forEach(srcDir -> {
            if (srcDir.equals(outputDir))
                return;
            System.out.println(srcDir.getAbsolutePath());
            this.generateSchema(srcDir, outputDir);
        });
        // System.out.println("Schema Generation completed");


    }
}

