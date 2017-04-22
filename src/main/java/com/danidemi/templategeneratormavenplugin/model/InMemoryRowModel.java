package com.danidemi.templategeneratormavenplugin.model;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRowModel implements IRowModel {

    private final Map<String, Object> data;
    private final RowMetaModel meta;

    InMemoryRowModel(Map<String, Object> data, RowMetaModel meta) {
        this.data = data;
        this.meta = meta;
    }

    @Override public Map<String, Object> getData() {
        return data;
    }

    @Override public RowMetaModel getMeta() {
        return meta;
    }

    public Map<String, Object> asMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("meta", meta);
        return result;
    }
}
