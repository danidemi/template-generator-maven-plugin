package com.danidemi.templategeneratormavenplugin.generation;

import java.util.Map;

public class IncludeAllRowFilter implements com.danidemi.templategeneratormavenplugin.generation.RowFilter {
    @Override public boolean keep(Map<String, Object> context) {
        return true;
    }
}
