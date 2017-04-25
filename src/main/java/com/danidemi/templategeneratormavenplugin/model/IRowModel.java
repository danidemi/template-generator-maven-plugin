package com.danidemi.templategeneratormavenplugin.model;

import java.util.Map;

public interface IRowModel {
    Map<String, Object> getData();

    IRowMetaModel getMeta();

}
