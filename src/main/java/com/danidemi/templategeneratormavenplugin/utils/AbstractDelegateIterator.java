package com.danidemi.templategeneratormavenplugin.utils;

import java.util.Iterator;

public abstract class AbstractDelegateIterator<E> implements Iterator<E> {

    protected final Iterator<E> delegate;

    public AbstractDelegateIterator(Iterator<E> delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    public E next() {
        return delegate.next();
    }

    @Override
    public void remove() {
        delegate.remove();
    }
}
