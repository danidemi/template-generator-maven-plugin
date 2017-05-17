package com.danidemi.templategeneratormavenplugin.model;

import java.util.Map;

public abstract class RowModelUtils {

    public static final String describe(IRowModel rowModel){
        Map<String, Object> data = rowModel.getData();
        IRowMetaModel meta = rowModel.getMeta();
        String reult = data.toString();
        reult = reult.substring(1, reult.length()-1);
        reult +=  " | count=" + meta.getCount() + ", sourceCount=" + meta.getSourceCount();
        return "{" + reult + "}";
    }

    private RowModelUtils() {
        throw new UnsupportedOperationException("Not meant to be instantiated");
    }

}
