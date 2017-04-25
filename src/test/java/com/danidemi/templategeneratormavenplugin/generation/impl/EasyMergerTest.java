package com.danidemi.templategeneratormavenplugin.generation.impl;

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
        assertEquals( "john", getString(sut, ctx, "${source.rows[0].data.name}" ));
        assertEquals( "3", getString(sut, ctx, "${source.rows.size()}" ));

    }

    private String getString(EasyMerger sut, ContextModel ctx, String inputStreamReader) {
        return sut.mergeTemplateIntoStringWriter(inputStreamReader , ctx).toString();
    }

}