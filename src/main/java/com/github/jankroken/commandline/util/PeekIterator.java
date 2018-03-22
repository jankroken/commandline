package com.github.jankroken.commandline.util;

import java.util.Iterator;

public class PeekIterator<T> implements Iterator<T> {

    private T nextValue;
    private final Iterator<T> iterator;

    public PeekIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    public boolean hasNext() {
        return nextValue != null || iterator.hasNext();
    }

    public T next() {
        if (nextValue != null) {
            var value = nextValue;
            nextValue = null;
            return value;
        } else {
            return iterator.next();
        }
    }

    public T peek() {
        if (nextValue == null) {
            nextValue = iterator.next();
        }
        return nextValue;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

}
