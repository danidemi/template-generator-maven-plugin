package com.danidemi.templategeneratormavenplugin.maven;

/*-
 * #%L
 * template-generator-maven-plugin
 * %%
 * Copyright (C) 2017 Studio DaniDemi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */

import com.danidemi.templategeneratormavenplugin.generation.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

@Mojo(name = "generate")
public class GenerateMojo extends AbstractMojo {

    public enum ContextMode {
        ONE_CONTEXT_PER_LINE,
        ONE_CONTEXT_PER_CSV
    }

    private Log log = getLog();

    @Parameter( property = "generate.pathToCsv", defaultValue = "/default.csv" )
    private String pathToCsv;

    @Parameter( property = "generate.pathToTemplate", defaultValue = "/default.csv" )
    private String pathToTemplate;

    @Parameter( property = "generate.pathToOutputFolder", defaultValue = "${build.directory}/generated-sources" )
    private String pathToOutputFolder;

    @Parameter( property = "generate.contextMode")
    private ContextMode contextMode;

    @Parameter( property = "generate.fileNameTemplate", required = true)
    private String fileNameTemplate;

    @Parameter( property = "generate.includeRowExpression", required = false)
    private String includeRowExpression;

    public void execute() throws MojoExecutionException, MojoFailureException {

        String pathToCsv = this.pathToCsv;
        String pathToTemplate = this.pathToTemplate;
        String pathToOutputFolder = this.pathToOutputFolder;
        String outputFileName = this.fileNameTemplate;
        String includeRowExpression = this.includeRowExpression;
        ContextMode contextMode = this.contextMode;


        log.info("Using CSV: '" + pathToCsv + "'");
        log.info("Using template: '" + pathToTemplate + "'");
        if(includeRowExpression!=null){
            log.info("Include only rows satisfing: '" + includeRowExpression + "'");
        }else{
            log.info("Include all rows.");
        }
        log.info("Context mode: '" + contextMode + "'");
        log.info("Output folder: '" + pathToOutputFolder + "'");
        log.info("Output file: '" + outputFileName + "'");


        ContextCreator ctxs;
        if (contextMode == ContextMode.ONE_CONTEXT_PER_CSV) {
            ctxs = OneContextPerCsvFile.fromFilepath(pathToCsv);
        } else if (contextMode == ContextMode.ONE_CONTEXT_PER_LINE) {
            ctxs = OneContextPerCsvLine.fromFilepath(pathToCsv);
        } else{
            throw new IllegalStateException("Unsupported mode");
        }
        Template tfc = Template.fromFilePath(pathToTemplate);
        FileStore fs = new FileStore( new File(pathToOutputFolder) );
        Merger contentMerger = new Merger(tfc, ctxs, fs);
        EasyMerger fileNameMerger = new EasyMerger();
        RowFilter rowFilter;
        if(includeRowExpression!=null){
            rowFilter = new JuelRowFilter(includeRowExpression);
        }else{
            rowFilter = new IncludeAllRowFilter();
        }

        // get the contexts
        for (Map<String, Object> context : ctxs) {

            if(rowFilter.discard(context)) continue;

            // build the content
            StringWriter content = contentMerger.mergeTemplateIntoStringWriter(tfc.asReader(), context);

            // store the file
            String fileName = fileNameMerger.mergeTemplateIntoStringWriter(this.fileNameTemplate, context).toString();
            fs.storeContentToFile(content, fileName);

        }

    }


}
