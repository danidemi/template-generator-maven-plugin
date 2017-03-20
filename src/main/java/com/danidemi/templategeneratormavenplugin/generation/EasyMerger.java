package com.danidemi.templategeneratormavenplugin.generation;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public class EasyMerger {

    public EasyMerger() {

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
