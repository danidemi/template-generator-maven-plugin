package com.danidemi.templategeneratormavenplugin.generation;

import java.util.Map;

/**
 * Implementations are able to provide a sequence of contexts.
 */
public interface ContextCreator extends Iterable<Map<String,Object>> {
}
