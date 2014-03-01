package com.github.jankroken.commandline.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {

    private T[] array;
    private int index = 0;

    public ArrayIterator(T[] array) {
        this.array = array;
    }

    public boolean hasNext() {
        return array.length > index;
    }

    public T next() {
        if (array.length > index) {
            return array[index++];
        } else {
            throw new NoSuchElementException();
        }
    }

    public T peek() {
        if (array.length > index) {
            return array[index];
        } else {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
