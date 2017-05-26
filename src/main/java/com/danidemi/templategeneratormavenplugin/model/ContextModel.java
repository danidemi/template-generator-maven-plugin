package com.danidemi.templategeneratormavenplugin.model;

/*-
 * #%L
 * template-generator-maven-plugin
 * %%
 * Copyright (C) 2017 Studio DaniDemi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    void setMeta(ContextMetaModel metaModel) {
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

    public List<IRowModel> getRows() {
        List<IRowModel> theRows = new ArrayList<>();
        for( Iterator<IRowModel> theIterator = rows.iterator(); theIterator.hasNext(); ){
            theRows.add( theIterator.next() );
        }
        return theRows;
    }
}
