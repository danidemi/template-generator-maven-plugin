package com.danidemi.templategeneratormavenplugin.generation.impl;

import com.danidemi.templategeneratormavenplugin.generation.RowFilter;
import com.danidemi.templategeneratormavenplugin.model.IRowModel;

public abstract class GuavaPredicateRowFilter implements RowFilter {

    @Override
    public final boolean keep(IRowModel row) {
        return apply(row);
    }

    @Override
    public final boolean test(IRowModel row) {
        return apply(row);
    }

    @Override
    public abstract boolean apply(IRowModel row);

}
