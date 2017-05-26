package com.danidemi.templategeneratormavenplugin.generation.impl;

/*-
 * #%L
 * template-generator-maven-plugin
 * %%
 * Copyright (C) 2017 Studio DaniDemi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */

import com.danidemi.templategeneratormavenplugin.model.ContextModel;
import com.danidemi.templategeneratormavenplugin.model.ContextModelBuilder;
import com.danidemi.templategeneratormavenplugin.model.InMemoryRowModel;
import com.danidemi.templategeneratormavenplugin.model.RowMetaModel;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EasyMergerTest {

    @Test
    public void shouldMergeATemplate() {

        // given
        EasyMerger sut = new EasyMerger();

        Map<String, Object> map = new HashMap<>();
        map.put("name", "john");
        RowMetaModel meta = null;
        ContextModel ctx = new ContextModelBuilder()
                .withTemplate(new File("a"))
                .withSource(new File("b"))
                .add( new InMemoryRowModel( map, meta ) )
                .add( new InMemoryRowModel( map, meta ) )
                .add( new InMemoryRowModel( map, meta ) )
                .build();

        // then
        assertEquals( "john", getString(sut, ctx, "${rows[0].data.name}" ));
        assertEquals( "3", getString(sut, ctx, "${rows.size()}" ));

    }

    private String getString(EasyMerger sut, ContextModel ctx, String inputStreamReader) {
        return sut.mergeTemplateIntoStringWriter(inputStreamReader , ctx).toString();
    }

}
