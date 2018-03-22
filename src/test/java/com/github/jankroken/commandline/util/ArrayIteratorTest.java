package com.github.jankroken.commandline.util;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static com.github.jankroken.commandline.util.Constants.EMPTY_STRING_ARRAY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayIteratorTest {

    @Test
    public void testEmpty() {
        var ai = new ArrayIterator<>(EMPTY_STRING_ARRAY);
        assertFalse(ai.hasNext());
    }

    @Test
    public void testOneElement() {
        var oneElement = new String[]{"hello"};
        var ai = new ArrayIterator<>(oneElement);
        assertTrue(ai.hasNext());
        var hello = ai.next();
        assertEquals(hello, "hello");
        assertFalse(ai.hasNext());
    }

    @Test
    public void testTwoElements() {
        var elements = new String[]{"hello", "world"};
        var ai = new ArrayIterator<>(elements);
        assertTrue(ai.hasNext());
        assertEquals(ai.next(), "hello");
        assertTrue(ai.hasNext());
        assertEquals(ai.next(), "world");
        assertFalse(ai.hasNext());
    }

    @Test
    public void testTwoElementsNoReadAhead() {
        var elements = new String[]{"hello", "world"};
        var ai = new ArrayIterator<>(elements);
        assertEquals(ai.next(), "hello");
        assertEquals(ai.next(), "world");
        assertFalse(ai.hasNext());
    }

    @Test
    public void testTwoElementsPeek() {
        var elements = new String[]{"hello", "world"};
        var ai = new ArrayIterator<>(elements);
        assertEquals(ai.peek(), "hello");
        assertEquals(ai.next(), "hello");
        assertEquals(ai.peek(), "world");
        assertEquals(ai.next(), "world");
        assertFalse(ai.hasNext());
    }


    @Test
    public void testReadPastEmpty() {
        var ai = new ArrayIterator<>(EMPTY_STRING_ARRAY);
        assertThatThrownBy(ai::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testPeekPastEmpty() {
        var ai = new ArrayIterator<>(EMPTY_STRING_ARRAY);
        assertThatThrownBy(ai::peek).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testReadPastTwo() {
        var elements = new String[]{"hello", "world"};
        var ai = new ArrayIterator<>(elements);
        assertEquals(ai.next(), "hello");
        assertEquals(ai.next(), "world");
        assertThatThrownBy(ai::next).isInstanceOf(NoSuchElementException.class);
    }


}
