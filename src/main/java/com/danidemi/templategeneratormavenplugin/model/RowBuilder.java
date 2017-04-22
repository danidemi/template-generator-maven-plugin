package com.danidemi.templategeneratormavenplugin.model;

import java.util.Map;

/**
 * Stateful builder that ease the creation of {@link InMemoryRowModel rows} keeping track of their progressive count.
 */
class RowBuilder {

    private int rowCount;

    RowBuilder(){
        rowCount = 1;
    }

    public IRowModel nextRow(Map<String, Object> data, int sourceCount) {
        RowMetaModel meta = new RowMetaModel(rowCount++, sourceCount);
        return new InMemoryRowModel(data, meta);
    }

}
