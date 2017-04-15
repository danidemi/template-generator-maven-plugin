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
        ContextModel sut = new ContextModel();

        // when
        Map<String, Object> row = new HashMap<>();
        sut.addRow( row, 13 );

        // then
        assertEquals( 1, sut.getRows().get(0).getMeta().getCount() );
        assertEquals( 0, sut.getRows().get(0).getMeta().getIndex() );
        assertEquals( 13, sut.getRows().get(0).getMeta().getSourceCount() );
        assertEquals( 12, sut.getRows().get(0).getMeta().getSourceIndex() );

    }

    @Test public void createContextWithFileInfo() throws IOException {

        // given
        File template = tmp.newFile("template.txt");
        File source = tmp.newFile("source.txt");
        File target = tmp.newFile("target.txt");

        // when
        ContextModel sut = new ContextModel()
                .withTemplate(template)
                .withSource(source)
                .withTarget(target);

        // then
        assertEquals(template.getAbsolutePath(), sut.getMeta().getTemplate().getAbsolutePath());
        assertEquals(target.getAbsolutePath(), sut.getMeta().getTarget().getAbsolutePath());
        assertEquals(source.getAbsolutePath(), sut.getMeta().getSource().getAbsolutePath());

    }

}