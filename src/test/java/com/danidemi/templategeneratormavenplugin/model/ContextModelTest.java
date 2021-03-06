package com.danidemi.templategeneratormavenplugin.model;

/*-
 * #%L
 * template-generator-maven-plugin
$Id:$
$HeadURL:$
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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ContextModelTest {

    @Rule public TemporaryFolder tmp = new TemporaryFolder();

    @Test public void createContext() throws IOException {

        // given
        ContextModelBuilder sut = new ContextModelBuilder();

        // when
        Map<String, Object> row = new HashMap<>();
        sut.toRows().add( row, 13 );
        sut.withSource(tmp.newFile());
        sut.withTemplate(tmp.newFile());
        ContextModel ctx = sut.build();

        // then
        IRowModel roww = ctx.rowIterator().iterator().next();
        assertEquals( 1, roww.getMeta().getCount() );
        assertEquals( 0, roww.getMeta().getIndex() );
        assertEquals( 13, roww.getMeta().getSourceCount() );
        assertEquals( 12, roww.getMeta().getSourceIndex() );

    }

    @Test public void createContextWithFileInfo() throws IOException {

        // given
        File template = tmp.newFile("template.txt");
        File source = tmp.newFile("source.txt");
        File target = tmp.newFile("target.txt");

        // when
        List<IRowModel> list = new ArrayList<>();
        ContextModel sut = new ContextModelBuilder()
                .toRows(list)
                .withSource(source)
                .withTemplate(template)
                .build();

        // then
        assertEquals(template.getAbsolutePath(), sut.getMeta().getTemplate().getAbsolutePath());
        assertEquals(source.getAbsolutePath(), sut.getMeta().getSource().getAbsolutePath());

    }

}
