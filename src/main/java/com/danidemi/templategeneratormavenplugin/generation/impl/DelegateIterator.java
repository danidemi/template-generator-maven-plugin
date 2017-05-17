package com.danidemi.templategeneratormavenplugin.generation.impl;

import java.util.Iterator;

public class DelegateIterator<E> implements Iterator<E> {

    protected final Iterator<E> iterator;

    public DelegateIterator(Iterator<E> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public E next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}
