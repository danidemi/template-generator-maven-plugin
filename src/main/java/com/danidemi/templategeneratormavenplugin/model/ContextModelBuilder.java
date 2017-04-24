package com.danidemi.templategeneratormavenplugin.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.checkState;

public class ContextModelBuilder implements Cloneable {

    private File template;
    private File source;
    private List<IRowModel> rows;
    private Iterable<IRowModel> iterable;
    private List<String> tags;
    volatile private ContextModel model;

    ContextModelBuilder(ContextModelBuilder toClone) {
        this.template = toClone.template;
        this.source = toClone.source;
        this.rows = toClone.rows != null ? new ArrayList<>(toClone.rows) : null;
        this.iterable = toClone.iterable;
        this.tags = toClone.tags != null ? new ArrayList<>(toClone.tags) : null;
    }

    public ContextModelBuilder(){

    }

    public ContextModel build() {

        if(model==null) {

            checkState(iterable != null ^ rows != null, "You should set either an iterable over rows or the rows.");
            checkState(template != null, "Please set the template file.");
            checkState(source != null, "Please set the source file.");

            ContextModel model = new ContextModel();

            // the metamodel
            ContextMetaModel metaModel = new ContextMetaModel(new FileModel(template), new FileModel(source));
            if (rows != null) {
                metaModel.setCount(new CountModel(rows.size()));
            }
            if (tags != null) {
                metaModel.setTags(new LinkedHashSet<>(tags));
            }
            model.setMetaModel(metaModel);

            // the rows
            if (iterable != null) {
                model.setRows(iterable);
            } else {
                model.setRows(rows);
            }

            this.model = model;
        }

        return model;

    }

    public ContextModelBuilder withTag(String tag) {

        checkState(model == null);

        if(tags!=null) this.tags = new ArrayList<>();
        this.tags.add(tag);
        return this;
    }

    public ContextModelBuilder withTemplate(File template) {

        checkState(model == null);

        this.template = template;
        return this;
    }

    public ContextModelBuilder withSource(File source) {

        checkState(model == null);

        this.source = source;
        return this;
    }

    public RowBuilder toRows(){
        checkState( iterable==null, "You should set either an iterable over rows or the rows." );
        RowBuilder rowBuilder = new RowBuilder(this);
        return rowBuilder;
    }

    public ContextModelBuilder toRows(List<IRowModel> rows) {

        checkState(model == null);

        checkState( iterable==null, "You should set either an iterable over rows or the rows." );
        this.iterable = rows;
        return this;
    }

    public ContextModelBuilder add(IRowModel row) {
        if(rows==null) rows = new ArrayList<>();
        rows.add(row);
        return this;
    }

    static public class RowBuilder {
        private ContextModelBuilder mainBuilder;
        private int rowCount;

        RowBuilder(ContextModelBuilder mainBuilder){
            this.mainBuilder = mainBuilder;
            rowCount = 1;
        }

        public RowBuilder add(Map<String, Object> data, long sourceCount) {

            checkState(mainBuilder.model == null);

            RowMetaModel meta = new RowMetaModel(rowCount++, sourceCount);
            IRowModel IRowModel = new InMemoryRowModel(data, meta);
            if(mainBuilder.rows == null){
                mainBuilder.rows = new ArrayList<>();
            }
            mainBuilder.rows.add(IRowModel);
            return this;
        }

    }

    @Override public Object clone() {
        return new ContextModelBuilder(this);
    }
}
