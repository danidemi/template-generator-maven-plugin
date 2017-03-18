package com.danidemi.templategeneratormavenplugin.generation;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.util.Map;

public class Merger {

    private final TemplateFromClasspath template;
    private final ContextCreator contexts;
    private final FileStore fileStore;

    public Merger(TemplateFromClasspath template, ContextCreator contexts, FileStore fileStore) {
        this.template = template;
        this.contexts = contexts;
        this.fileStore = fileStore;
    }

    private void merge() {

        Reader templateReader = template.asReader();

        // get the contexts
        for (Map<String, Object> context : contexts) {

            // build the content
            StringWriter content = mergeTemplateIntoStringWriter(templateReader, context);

            // store the file
            fileStore.store(content);

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
