package com.danidemi.templategeneratormavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.net.URL;
import java.util.Map;

@Mojo(name = "generate") public class Generate extends AbstractMojo {

    @Parameter( property = "context")
    private String template;

    @Parameter
    private ContextCreator contexts;

    @Parameter( property = "template")
    private Templating templating;

    @Parameter( property = "output" )
    private String outputPath;

    public void execute() throws MojoExecutionException, MojoFailureException {

        InputStreamReader inputStreamReader = null;
        try {
            URL resource = getClass().getResource(template);
            String file = new File( resource.getFile() ).getAbsolutePath();
            inputStreamReader = new InputStreamReader(resource.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Map<String, Object> context : contexts) {
            StringWriter sw = mergeTemplateIntoStringWriter(inputStreamReader, context);
            StringWriter finalPath = mergeTemplateIntoStringWriter(new StringReader(outputPath), context);

            try {
                File file = new File(finalPath.toString());
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = sw.toString().getBytes();
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private StringWriter mergeTemplateIntoStringWriter(Reader inputStreamReader, Map<String, Object> context) {
        StringWriter sw = new StringWriter();
        final VelocityContext vcontext = new VelocityContext();
        context.forEach( (k,v) -> vcontext.put(k,v)  );
        Velocity.evaluate(vcontext, sw, "logtag", inputStreamReader);
        return sw;
    }
}