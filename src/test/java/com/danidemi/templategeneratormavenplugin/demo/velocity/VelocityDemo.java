package com.danidemi.templategeneratormavenplugin.demo.velocity;

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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Test;

import java.io.File;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

public class VelocityDemo {

    @Test
    public void run() {
        Velocity.init();
        VelocityContext context = new VelocityContext();
        context.put("name", new String("Velocity"));
        Template template = null;
        URL resource = null;
        try {
            resource = getClass().getResource("/mytemplate.vm");
            System.out.println(resource);
            String file = new File(resource.getFile()).getAbsolutePath();
            System.out.println(file);
            StringWriter sw = new StringWriter();
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
            // other problems here
        }
    }

}
