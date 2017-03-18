package com.danidemi.templategeneratormavenplugin.maven;

import com.danidemi.templategeneratormavenplugin.generation.ContextCreator;
import com.danidemi.templategeneratormavenplugin.generation.Templating;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

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




    }


}