package com.danidemi.templategeneratormavenplugin.generation;

import java.util.Map;

public class ExcludeFilter implements RowFilter {

    private final RowFilter rowFilter;

    public ExcludeFilter(RowFilter rowFilter) {
        this.rowFilter = rowFilter;
    }

    @Override
    public boolean keep(Map<String, Object> context) {
        return !rowFilter.keep(context);
    }
}
