package com.danidemi.templategeneratormavenplugin.generation;

import java.util.Iterator;
import java.util.function.Function;

import static com.danidemi.templategeneratormavenplugin.utils.Preconditions.validateArgumentNotNull;

/**
 * Make a new {@link Iterator} out of an existing one, transforming all its element using a provided transformation function.
 */
class TransformIteratorAdapter<Original, Tranformed> implements Iterator<Tranformed> {

    private final Iterator<Original> iterator;
    private final Function<Original, Tranformed> mappingFunction;

    TransformIteratorAdapter(Iterator<Original> iterator, Function<Original, Tranformed> mappingFunction) {
        this.iterator = validateArgumentNotNull(iterator);
        this.mappingFunction = validateArgumentNotNull(mappingFunction);
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Tranformed next() {
        return mappingFunction.apply(iterator.next());
    }
}
