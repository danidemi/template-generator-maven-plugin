package com.danidemi.templategeneratormavenplugin.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.checkState;

public class ContextModelBuilder {

    private File template;
    private File source;
    private File target;
    private List<RowModel> rows;
    private Iterable<RowModel> iterable;
    private List<String> tags;
    private ContextModel model;

    public ContextModel build() {

        if(model==null) {

            checkState(iterable != null ^ rows != null, "You should set either an iterable over rows or the rows.");
            checkState(template != null);
            checkState(source != null);
            checkState(target != null);

            ContextModel model = new ContextModel();

            // the metamodel
            ContextMetaModel metaModel = new ContextMetaModel(new FileModel(template), new FileModel(source), new FileModel(target));
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

    public ContextModelBuilder withTarget(File target) {

        checkState(model == null);

        this.target = target;
        return this;
    }

    public RowBuilder toRows(){
        checkState( iterable==null, "You should set either an iterable over rows or the rows." );
        RowBuilder rowBuilder = new RowBuilder(this);
        return rowBuilder;
    }

    public ContextModelBuilder toRows(List<RowModel> rows) {

        checkState(model == null);

        checkState( iterable==null, "You should set either an iterable over rows or the rows." );
        this.iterable = rows;
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
            RowModel rowModel = new RowModel(data, meta);
            if(mainBuilder.rows == null){
                mainBuilder.rows = new ArrayList<>();
            }
            mainBuilder.rows.add(rowModel);
            return this;
        }

    }

}
