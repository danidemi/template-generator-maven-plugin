package com.danidemi.templategeneratormavenplugin.maven;

import com.danidemi.templategeneratormavenplugin.generation.CsvContextCreator;
import com.danidemi.templategeneratormavenplugin.generation.FileStore;
import com.danidemi.templategeneratormavenplugin.generation.Merger;
import com.danidemi.templategeneratormavenplugin.generation.Template;
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

    Log log = getLog();

    @Parameter( property = "generate.pathToCsv", defaultValue = "/default.csv" )
    private String pathToCsv;

    @Parameter( property = "generate.pathToTemplate", defaultValue = "/default.csv" )
    private String pathToTemplate;

    @Parameter( property = "generate.pathToOutputFolder", defaultValue = "${build.directory}/generated-sources" )
    private String pathToOutputFolder;

    public void execute() throws MojoExecutionException, MojoFailureException {

        String pathToCsv = this.pathToCsv;
        String pathToTemplate = this.pathToTemplate;
        String pathToOutputFolder = this.pathToOutputFolder;

        log.info("Path to CSV: '" + pathToCsv + "'");
        log.info("Path to Template: '" + pathToTemplate + "'");
        log.info("Path to output: '" + pathToOutputFolder + "'");

        log.info("START");
        CsvContextCreator ctxs = CsvContextCreator.fromFilepath(pathToCsv);
        Template tfc = Template.fromFilePath(pathToTemplate);
        FileStore fs = new FileStore( new File(pathToOutputFolder) );
        Merger m = new Merger(tfc, ctxs, fs);

        // get the contexts
        for (Map<String, Object> context : ctxs) {

            // build the content
            StringWriter content = m.mergeTemplateIntoStringWriter(tfc.asReader(), context);

            log.info("content:\n" + content);

            // store the file
            fs.store(content);

        }

        m.merge();
        log.info("END");


    }


}