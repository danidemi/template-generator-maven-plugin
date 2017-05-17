package com.danidemi.templategeneratormavenplugin.model;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RowModelUtilsTest {

    @Test
    public void shouldDescribe() throws Exception {

        // given
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("k1", "v1");
        data.put("k2", "v2");

        RowMetaModel meta = new RowMetaModel(100, 120);
        InMemoryRowModel rowModel = new InMemoryRowModel(data, meta);

        // when
        String describe = RowModelUtils.describe(rowModel);

        // then
        assertEquals( "{k1=v1, k2=v2 | count=100, sourceCount=120}", describe );

    }

}