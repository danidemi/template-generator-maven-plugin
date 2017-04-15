package com.danidemi.templategeneratormavenplugin.model;

import java.util.Map;

public class RowModel {

    private final Map<String, Object> data;
    private final RowMetaModel meta;

    public RowModel(Map<String, Object> data, RowMetaModel meta) {
        this.data = data;
        this.meta = meta;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public RowMetaModel getMeta() {
        return meta;
    }
}
