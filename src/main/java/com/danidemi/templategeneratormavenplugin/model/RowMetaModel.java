package com.danidemi.templategeneratormavenplugin.model;

public class RowMetaModel {
    private final long count;
    private final long sourceCount;

    RowMetaModel(int count, long sourceCount) {
        this.count = count;
        this.sourceCount = sourceCount;
    }

    public long getCount() {
        return count;
    }

    public long getIndex() {
        return count-1;
    }

    public long getSourceIndex() {
        return sourceCount-1;
    }

    public long getSourceCount() {
        return sourceCount;
    }
}
