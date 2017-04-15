package com.danidemi.templategeneratormavenplugin.model;

public class CountModel {
    private final int rowCount;

    public CountModel(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRows(){
        return this.rowCount;
    }

    public int getLastIndex(){
        return getRows()-1;
    }
}
