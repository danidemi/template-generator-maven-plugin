package com.danidemi.templategeneratormavenplugin.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ContextModel {

    private final List<RowModel> rows = new ArrayList<>();
    private File template;
    private File source;
    private File target;
    private ContextMetaModel meta = null;

    public void addRow(Map<String, Object> row, int sourceRecord) {

        int rowsAlreadyThere = rows.size();

        RowModel rowModel = new RowModel(
                row,
                new RowMetaModel(++rowsAlreadyThere, sourceRecord)
        );

        this.rows.add(rowModel);
    }

    public List<RowModel> getRows() {
        return rows;
    }

    public ContextModel withTemplate(File template) {
        this.template = template;
        return this;
    }

    public ContextModel withSource(File source) {
        this.source = source;
        return this;
    }

    public ContextModel withTarget(File target) {
        this.target = target;
        return this;
    }

    public ContextMetaModel getMeta() {
        if(meta==null){
            LinkedHashSet<String> tags = new LinkedHashSet<>(0);
            meta = new ContextMetaModel( new FileModel( template ), new FileModel( source ), new FileModel( target ), new CountModel(rows.size()), tags);
        }
        return meta;
    }
}
