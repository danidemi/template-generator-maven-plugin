package com.danidemi.templategeneratormavenplugin.maven;

/*-
 * #%L
 * template-generator-maven-plugin
 * %%
 * Copyright (C) 2017 Studio DaniDemi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.danidemi.templategeneratormavenplugin.generation.*;
import com.danidemi.templategeneratormavenplugin.generation.impl.*;
import com.danidemi.templategeneratormavenplugin.model.ContextModel;
import com.danidemi.templategeneratormavenplugin.model.ContextModelBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.Arrays;

@Mojo(name = "generate")
public class GenerateMojo extends AbstractMojo {

    public enum ContextMode {
        ONE_CONTEXT_PER_LINE,
        ONE_CONTEXT_PER_CSV,
        ONE_CONTEXT_PER_TAG
    }

    private Log log = getLog();

    @Parameter( property = "generate.pathToCsv", required = true )
    private String pathToCsv;

    @Parameter( property = "generate.pathToTemplate", required = true )
    private String pathToTemplate;

    @Parameter( property = "generate.pathToOutputFolder", defaultValue = "${build.directory}/generated-sources" )
    private String pathToOutputFolder;

    @Parameter( property = "generate.contextMode")
    private ContextMode contextMode;

    @Parameter( property = "generate.fileNameTemplate", required = true)
    private String fileNameTemplate;

    @Parameter( property = "generate.includeRowExpression", required = false)
    private String includeRowExpression;

    @Parameter( property = "generate.includeContextExpression", required = false)
    private String includeContextExpression;

    @Parameter( property = "generate.tagExpressions", required = false)
    private String[] tagExpressions;

    public void execute() throws MojoExecutionException, MojoFailureException {

        String pathToCsv = this.pathToCsv;
        String pathToTemplate = this.pathToTemplate;
        String pathToOutputFolder = this.pathToOutputFolder;
        String outputFileName = this.fileNameTemplate;
        String includeRowExpression = this.includeRowExpression != null ? this.includeRowExpression.replace("@{", "${") : null;
        String includeContextExpression = this.includeContextExpression != null ? this.includeContextExpression.replace("@{", "${") : null;
        ContextMode contextMode = this.contextMode;


        log.info("Using CSV: '" + pathToCsv + "'");
        log.info("Using template: '" + pathToTemplate + "'");
        if(includeRowExpression!=null){
            log.info("Include only rows satisfing 2: '" + includeRowExpression + "'");
        }else{
            log.info("Include all rows.");
        }
        log.info("Context mode: '" + contextMode + "'");
        log.info("Output folder: '" + pathToOutputFolder + "'");
        log.info("Output file: '" + outputFileName + "'");
        if(includeContextExpression!=null){
            log.info("Include only contexts satisfying: '" + includeContextExpression + "'");
        }else{
            log.info("Include all contexts.");
        }


        RowFilter rowFilter;
        if(includeRowExpression!=null){
            rowFilter = new JuelRowFilter( includeRowExpression );
        }else{
            rowFilter = new IncludeAllRowFilter();
        }

        // row source
        RowSource rowSource;
        try {
            rowSource = new FilteredRowSource( new CsvRowSource(new FileReader(new File(pathToCsv))), rowFilter );
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("An error occurred while accessing file '" + pathToCsv + "'", e);
        }

        ContextModelBuilder builderPrototype = new ContextModelBuilder()
                .withSource( new File(pathToCsv) )
                .withTemplate( new File(pathToTemplate));

        ContextCreator contextCreator;
        if (contextMode == ContextMode.ONE_CONTEXT_PER_CSV) {
            contextCreator = new OneContextPerCsvFile(rowSource, builderPrototype);
        } else if (contextMode == ContextMode.ONE_CONTEXT_PER_LINE) {
            contextCreator = new OneContextPerCsvLineStaxLike(rowSource, null);
        } else if(contextMode == ContextMode.ONE_CONTEXT_PER_TAG) {
            {
                if (this.tagExpressions == null || this.tagExpressions.length == 0) {
                    throw new MojoExecutionException("A list of tagExpressions are required when using " + ContextMode.ONE_CONTEXT_PER_TAG);
                }
                OneContextPerTag ocpt = new OneContextPerTag( Arrays.asList( tagExpressions ), rowSource, null);
                contextCreator = ocpt;
            }
        } else{
            throw new IllegalStateException("Unsupported mode");
        }
        Template tfc = Template.fromFilePath(pathToTemplate);
        FileStore fs = new FileStore( new File(pathToOutputFolder) );
        Merger contentMerger = new Merger(tfc, contextCreator, fs);
        EasyMerger fileNameMerger = new EasyMerger();

        // get the contexts
        JuelEval<Boolean> keepContextEval = includeContextExpression!=null ? new JuelEval<>() : null;
        int i = 0;
        boolean contextKept;
        for (ContextModel contextModel : contextCreator.contexts()) {

            // store the file
            String fileName = fileNameMerger
                    .mergeTemplateIntoStringWriter(this.fileNameTemplate, contextModel)
                    .toString();

            contextKept = true;
            if(keepContextEval!=null){
                contextKept = keepContextEval.invoke(contextModel, includeContextExpression);
            }
            if(!contextKept){
                log.info("Context discarded.");
                continue;
            }

            log.info("Context " + i + ": " + contextModel);

            // build the content
            StringWriter content = contentMerger.mergeTemplateIntoStringWriter(tfc.asReader(), contextModel);

            // store the file
            fs.storeContentToFile(content, fileName);

        }

    }


}
