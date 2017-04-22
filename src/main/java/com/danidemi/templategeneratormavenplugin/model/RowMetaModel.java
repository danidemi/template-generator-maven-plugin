package com.danidemi.templategeneratormavenplugin.model;

public class RowMetaModel implements IRowMetaModel {
    private final long count;
    private final long sourceCount;

    RowMetaModel(int count, long sourceCount) {
        this.count = count;
        this.sourceCount = sourceCount;
    }

    @Override
    public long getCount() {
        return count;
    }

    @Override
    public long getIndex() {
        return count-1;
    }

    @Override
    public long getSourceIndex() {
        return sourceCount-1;
    }

    @Override
    public long getSourceCount() {
        return sourceCount;
    }
}
