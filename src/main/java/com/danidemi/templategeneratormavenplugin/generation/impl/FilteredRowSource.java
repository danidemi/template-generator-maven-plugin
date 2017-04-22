package com.danidemi.templategeneratormavenplugin.generation.impl;

import com.danidemi.templategeneratormavenplugin.generation.RowSource;
import com.danidemi.templategeneratormavenplugin.model.IRowModel;
import com.danidemi.templategeneratormavenplugin.utils.FilterIteratorAdapter;

import java.util.Iterator;
import java.util.function.Predicate;

public class FilteredRowSource implements RowSource {

    private final RowSource rowSource;
    private final Predicate<IRowModel> predicate;

    public FilteredRowSource(RowSource rowSource, Predicate<IRowModel> predicate) {
        this.rowSource = rowSource;
        this.predicate = predicate;
    }

    @Override public Iterator<IRowModel> iterator() {
        return new FilterIteratorAdapter<>(rowSource.iterator(), predicate);
    }
}
