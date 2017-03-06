package com.danidemi.templategeneratormavenplugin.generation;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.File;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

public class TemplateRun {

    public static void main(String[] args) {
        new TemplateRun().run();
    }

    private void run() {
        Velocity.init();
        VelocityContext context = new VelocityContext();
        context.put("name", new String("Velocity"));
        Template template = null;
        URL resource = null;
        try {
            resource = getClass().getResource("/mytemplate.vm");
            System.out.println(resource);
            String file = new File( resource.getFile() ).getAbsolutePath();
            System.out.println(file);

            //template = Velocity.getTemplate(file);
            StringWriter sw = new StringWriter();
            //template.merge(context, sw);
            Velocity.evaluate(context, sw, "logtag", new InputStreamReader(resource.openStream()));
            System.out.println(sw);
        } catch (ResourceNotFoundException rnfe) {
            // couldn't find the template
        } catch (ParseErrorException pee) {
            // syntax error: problem parsing the template
        } catch (MethodInvocationException mie) {
            // something invoked in the template
            // threw an exception
        } catch (Exception e) {
        }
    }

}
