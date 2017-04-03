package com.danidemi.templategeneratormavenplugin.generation;

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

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.util.Map;

public class Merger {

    private final Template template;
    private final ContextCreator contexts;
    private final FileStore fileStore;

    public Merger(Template template, ContextCreator contexts, FileStore fileStore) {
        this.template = template;
        this.contexts = contexts;
        this.fileStore = fileStore;
    }

    public void merge() {

        Reader templateReader = template.asReader();

        // get the contexts
        for (Map<String, Object> context : contexts) {

            // build the content
            StringWriter content = mergeTemplateIntoStringWriter(templateReader, context);

            // store the file
            String fileName = "thepath." + (int) (Math.random() * 100) + ".java";
            fileStore.storeContentToFile(content, fileName);

        }
    }

    public StringWriter mergeTemplateIntoStringWriter(Reader inputStreamReader, Map<String, Object> context) {
        StringWriter sw = new StringWriter();
        final VelocityContext vcontext = new VelocityContext();
        context.forEach( (k,v) -> vcontext.put(k,v)  );
        Velocity.evaluate(vcontext, sw, "logtag", inputStreamReader);
        return sw;
    }

    public StringWriter mergeTemplateIntoStringWriter(String inputStreamReader, Map<String, Object> context) {
        return mergeTemplateIntoStringWriter( new StringReader(inputStreamReader), context );
    }
}
