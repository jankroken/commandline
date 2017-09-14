package com.github.jankroken.commandline.util;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class PeekIteratorTest {

	private PeekIterator<String> createIterator(String[] args) {
        ArrayIterator<String> ai = new ArrayIterator<>(args);
        return new PeekIterator<>(ai);
    }

    @Test
    public void testEmpty() {
        String[] empty = new String[0];
        PeekIterator<String> ai = createIterator(empty);
        assertFalse(ai.hasNext());
    }

    @Test
    public void testOneElement() {
        String[] oneElement = new String[]{"hello"};
        PeekIterator<String> ai = createIterator(oneElement);
        assertTrue(ai.hasNext());
        String hello = ai.next();
        assertEquals(hello,"hello");
        assertFalse(ai.hasNext());
    }
    
    @Test
    public void testTwoElements() {
    	String[] elements = new String[]{"hello","world"};
    	PeekIterator<String> ai = createIterator(elements);
        assertTrue(ai.hasNext());
        assertEquals(ai.next(),"hello");
        assertTrue(ai.hasNext());
        assertEquals(ai.next(),"world");
        assertFalse(ai.hasNext());
    }
   
    @Test
    public void testTwoElementsNoReadAhead() {
    	String[] elements = new String[]{"hello","world"};
    	PeekIterator<String> ai = createIterator(elements);
        assertEquals(ai.next(),"hello");
        assertEquals(ai.next(),"world");
        assertFalse(ai.hasNext());
    }

    @Test
    public void testTwoElementsPeek() {
    	String[] elements = new String[]{"hello","world"};
    	PeekIterator<String> ai = createIterator(elements);
        assertEquals(ai.peek(),"hello");
        assertEquals(ai.next(),"hello");
        assertEquals(ai.peek(),"world");
        assertEquals(ai.next(),"world");
        assertFalse(ai.hasNext());
    }


    @Test
    public void testReadPastEmpty() {
        String[] empty = new String[0];
        PeekIterator<String> ai = createIterator(empty);
        assertThatThrownBy(ai::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testPeekPastEmpty() {
        String[] empty = new String[0];
        PeekIterator<String> ai = createIterator(empty);
        assertThatThrownBy(ai::peek).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testReadPastTwo() {
    	String[] elements = new String[]{"hello","world"};
    	PeekIterator<String> ai = createIterator(elements);
        assertEquals(ai.next(),"hello");
        assertEquals(ai.next(),"world");
        assertThatThrownBy(ai::next).isInstanceOf(NoSuchElementException.class);
    }

}
