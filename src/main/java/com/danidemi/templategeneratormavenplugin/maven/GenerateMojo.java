package com.danidemi.templategeneratormavenplugin.maven;

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

    public void execute() throws MojoExecutionException, MojoFailureException {

        String pathToCsv = this.pathToCsv;
        String pathToTemplate = this.pathToTemplate;
        String pathToOutputFolder = this.pathToOutputFolder;

        log.info("Path to CSV: '" + pathToCsv + "'");
        log.info("Path to Template: '" + pathToTemplate + "'");
        log.info("Path to output: '" + pathToOutputFolder + "'");
        log.info("CTX mode: '" + this.contextMode + "'");
        log.info("FileName template: '" + fileNameTemplate + "'");

        log.info("START");

        ContextCreator ctxs;
        if (this.contextMode == ContextMode.ONE_CONTEXT_PER_CSV) {
            ctxs = OneContextPerCsvFile.fromFilepath(pathToCsv);
        } else if (this.contextMode == ContextMode.ONE_CONTEXT_PER_LINE) {
            ctxs = OneContextPerCsvLine.fromFilepath(pathToCsv);
        } else{
            throw new IllegalStateException("Unsupported mode");
        }

        Template tfc = Template.fromFilePath(pathToTemplate);
        FileStore fs = new FileStore( new File(pathToOutputFolder) );
        Merger contentMerger = new Merger(tfc, ctxs, fs);
        EasyMerger fileNameMerger = new EasyMerger();

        // get the contexts
        for (Map<String, Object> context : ctxs) {

            // build the content
            StringWriter content = contentMerger.mergeTemplateIntoStringWriter(tfc.asReader(), context);

            log.info("content:\n" + content);

            // store the file
            String fileName = fileNameMerger.mergeTemplateIntoStringWriter(this.fileNameTemplate, context).toString();
            fs.storeContentToFile(content, fileName);

        }


        log.info("END");


    }


}