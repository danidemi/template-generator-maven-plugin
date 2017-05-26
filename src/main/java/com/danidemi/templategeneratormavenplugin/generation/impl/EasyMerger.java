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

import com.danidemi.templategeneratormavenplugin.model.ContextModel;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

public class EasyMerger {

    public EasyMerger() {

    }

    public StringWriter mergeTemplateIntoStringWriter(Reader inputStreamReader, ContextModel context) {
        StringWriter sw = new StringWriter();
        final VelocityContext vcontext = new VelocityContext();
        //vcontext.put("source", context);
        vcontext.put("rows", context.getRows());
        vcontext.put("meta", context.getMeta());
        Velocity.evaluate(vcontext, sw, "logtag", inputStreamReader);
        return sw;
    }

    public StringWriter mergeTemplateIntoStringWriter(String inputStreamReader, ContextModel contextModel) {
        return mergeTemplateIntoStringWriter( new StringReader(inputStreamReader), contextModel );
    }
}
