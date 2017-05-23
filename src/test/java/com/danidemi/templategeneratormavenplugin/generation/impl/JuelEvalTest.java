package com.danidemi.templategeneratormavenplugin.generation.impl;

import com.danidemi.templategeneratormavenplugin.model.*;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JuelEvalTest {

    @Test
    public void shouldExecuteAComplexExpression() {

        // given
        ContextModel ctx = new ContextModelBuilder()
                .add(getiRowModel(getStringObjectMap("John", 23), 0))
                .add(getiRowModel(getStringObjectMap("Jean", 32), 0))
                .withTemplate(new File("a"))
                .withSource(new File("a"))
                .build();

        String expression = "${rows[0].data.Name.startsWith(\"BG\")}";


        // when
        Boolean invoke = new JuelEval<Boolean>().invoke(ctx, expression);

        // then
        assertFalse( invoke );

    }

    @Test
    public void shouldExecuteAnotherComplexExpression() {

        // given
        ContextModel ctx = new ContextModelBuilder()
                .add(getiRowModel(getStringObjectMap("John", 23), 0))
                .add(getiRowModel(getStringObjectMap("Jean", 32), 0))
                .withTemplate(new File("a"))
                .withSource(new File("a"))
                .build();

        String expression = "${meta==null}";


        // when
        Boolean invoke = new JuelEval<Boolean>().invoke(ctx, expression);

        // then
        assertFalse( invoke );

    }

    private IRowModel getiRowModel(Map<String, Object> mapRow, int count) {
        return new InMemoryRowModel(mapRow, new RowMetaModel(count, count));
    }

    private Map<String, Object> getStringObjectMap(String name, int age) {
        Map<String, Object> mapRow = new HashMap<>();
        mapRow.put("Name", name);
        mapRow.put("Age", age);
        return mapRow;
    }

}