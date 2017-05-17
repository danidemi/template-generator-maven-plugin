package com.danidemi.templategeneratormavenplugin.generation;

import com.danidemi.templategeneratormavenplugin.model.IRowModel;

public abstract class AbstractRowFilter implements RowFilter {

    @Override
    public boolean test(IRowModel t) {
        return keep(t);
    }

}
