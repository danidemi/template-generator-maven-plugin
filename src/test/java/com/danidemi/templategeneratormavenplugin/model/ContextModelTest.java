package com.danidemi.templategeneratormavenplugin.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ContextModelTest {

    @Rule public TemporaryFolder tmp = new TemporaryFolder();

    @Test public void createContext() {

        // given
        ContextModelBuilder sut = new ContextModelBuilder();

        // when
        Map<String, Object> row = new HashMap<>();
        sut.toRows().add( row, 13 );
        ContextModel ctx = sut.build();

        // then
        RowModel roww = ctx.rowIterator().iterator().next();
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
        ContextModel sut = new ContextModelBuilder()
                .withSource(source)
                .withTarget(target).build();

        // then
        assertEquals(template.getAbsolutePath(), sut.getMeta().getTemplate().getAbsolutePath());
        assertEquals(target.getAbsolutePath(), sut.getMeta().getTarget().getAbsolutePath());
        assertEquals(source.getAbsolutePath(), sut.getMeta().getSource().getAbsolutePath());

    }

}