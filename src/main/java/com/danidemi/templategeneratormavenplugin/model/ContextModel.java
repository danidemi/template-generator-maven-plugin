package com.danidemi.templategeneratormavenplugin.model;

import java.io.File;
import java.util.Map;

/**
 * To create a {@link ContextModel context} please use {@link ContextModelBuilder}.
 */
public class ContextModel {

    private Iterable<IRowModel> rows;
    private File template;
    private File source;
    private File target;
    private ContextMetaModel meta = null;

    void setTemplate(File template) {
        this.template = template;
    }

    void setSource(File source) {
        this.source = source;
    }

    void setTarget(File target) {
        this.target = target;
    }

    void setMetaModel(ContextMetaModel metaModel) {
        this.meta = metaModel;
    }

    void setRows(Iterable<IRowModel> rows) {
        this.rows = rows;
    }

    public ContextMetaModel getMeta() {
        return meta;
    }

    public Iterable<IRowModel> rowIterator() {
        return rows;
    }

    public Map<String, Object> asMap() {
        throw new UnsupportedOperationException();
    }
}
