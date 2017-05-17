package com.danidemi.templategeneratormavenplugin.generation.impl;

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

import java.io.*;
import java.net.URL;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.checkArgument;

public class Template {

    private final File templateLocator;

    private Template(File templateLocator) {
        checkArgument(templateLocator!=null, "Null argument.");
        checkArgument(templateLocator.exists(), "File %s does not exist.", templateLocator.getAbsoluteFile());
        checkArgument(templateLocator.canRead(), "File %s is not readable.", templateLocator.getAbsoluteFile());
        this.templateLocator = templateLocator;
    }

    public static Template fromClasspath(String templateLocator){
        URL resource = Template.class.getResource(templateLocator);
        File file = new File( resource.getFile() );
        return new Template(file);
    }

    public static Template fromFilePath(String templateLocator){
        File file = new File( templateLocator );
        return new Template(file);
    }

    public Reader asReader() {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader( new FileInputStream( templateLocator ) );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inputStreamReader;
    }

    /**
     * Returns the time that the template was last modified.
     */
    public long lastModified() {
        return templateLocator.lastModified();
    }
}
