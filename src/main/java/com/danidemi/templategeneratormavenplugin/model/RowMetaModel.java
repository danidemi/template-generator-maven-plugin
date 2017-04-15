package com.danidemi.templategeneratormavenplugin.model;

public class RowMetaModel {
    private final int count;
    private final int sourceCount;

    public RowMetaModel(int count, int sourceCount) {
        this.count = count;
        this.sourceCount = sourceCount;
    }

    public int getCount() {
        return count;
    }

    public int getIndex() {
        return count-1;
    }

    public int getSourceIndex() {
        return sourceCount-1;
    }

    public int getSourceCount() {
        return sourceCount;
    }
}
