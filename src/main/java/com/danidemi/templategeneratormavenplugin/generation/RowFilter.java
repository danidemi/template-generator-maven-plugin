package com.danidemi.templategeneratormavenplugin.generation;

import java.util.Map;

public interface RowFilter {

    boolean discard(Map<String, Object> context);

}
