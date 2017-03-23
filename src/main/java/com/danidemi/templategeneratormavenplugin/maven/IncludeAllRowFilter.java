package com.danidemi.templategeneratormavenplugin.maven;

import java.util.Map;

public class IncludeAllRowFilter implements com.danidemi.templategeneratormavenplugin.generation.RowFilter {
    @Override public boolean discard(Map<String, Object> context) {
        return false;
    }
}
