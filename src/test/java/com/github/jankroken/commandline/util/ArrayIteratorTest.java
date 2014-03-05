package com.github.jankroken.commandline.util;

import org.junit.Test;

import static org.junit.Assert.*;


public class ArrayIteratorTest {

    @Test
    public void testEmpty() {
        String[] empty = new String[0];
        ArrayIterator<String> ai = new ArrayIterator<>(empty);
        assertFalse(ai.hasNext());
    }

    @Test
    public void testOneElement() {
        String[] oneElement = new String[]{"hello"};
        ArrayIterator<String> ai = new ArrayIterator<>(oneElement);
        assertTrue(ai.hasNext());
        String hello = ai.next();
        assertEquals(hello, "hello");
        assertFalse(ai.hasNext());
    }

    @Test
    public void testTwoElements() {
        String[] elements = new String[]{"hello", "world"};
        ArrayIterator<String> ai = new ArrayIterator<>(elements);
        assertTrue(ai.hasNext());
        assertEquals(ai.next(), "hello");
        assertTrue(ai.hasNext());
        assertEquals(ai.next(), "world");
        assertFalse(ai.hasNext());
    }

    @Test
    public void testTwoElementsNoReadAhead() {
        String[] elements = new String[]{"hello", "world"};
        ArrayIterator<String> ai = new ArrayIterator<>(elements);
        assertEquals(ai.next(), "hello");
        assertEquals(ai.next(), "world");
        assertFalse(ai.hasNext());
    }

    @Test
    public void testTwoElementsPeek() {
        String[] elements = new String[]{"hello", "world"};
        ArrayIterator<String> ai = new ArrayIterator<>(elements);
        assertEquals(ai.peek(), "hello");
        assertEquals(ai.next(), "hello");
        assertEquals(ai.peek(), "world");
        assertEquals(ai.next(), "world");
        assertFalse(ai.hasNext());
    }


    @Test(expected = java.util.NoSuchElementException.class)
    public void testReadPastEmpty() {
        String[] empty = new String[0];
        ArrayIterator<String> ai = new ArrayIterator<>(empty);
        ai.next();
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testPeekPastEmpty() {
        String[] empty = new String[0];
        ArrayIterator<String> ai = new ArrayIterator<>(empty);
        ai.peek();
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testReadPastTwo() {
        String[] elements = new String[]{"hello", "world"};
        ArrayIterator<String> ai = new ArrayIterator<>(elements);
        assertEquals(ai.next(), "hello");
        assertEquals(ai.next(), "world");
        ai.next();
    }


}
