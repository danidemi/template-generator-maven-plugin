package com.danidemi.templategeneratormavenplugin.generation;

import java.util.Map;

public interface RowFilter {

    boolean keep(Map<String, Object> context);

}
