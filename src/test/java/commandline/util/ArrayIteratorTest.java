package commandline.util;

import org.junit.Test;

import com.jankroken.commandline.util.ArrayIterator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class ArrayIteratorTest {

    @Test
    public void testEmpty() {
        String[] empty = new String[0];
        ArrayIterator<String> ai = new ArrayIterator<String>(empty);
        assertFalse(ai.hasNext());
    }

    @Test
    public void testOneElement() {
        String[] oneElement = new String[]{"hello"};
        ArrayIterator<String> ai = new ArrayIterator<String>(oneElement);
        assertTrue(ai.hasNext());
        String hello = ai.next();
        assertEquals(hello,"hello");
        assertFalse(ai.hasNext());
    }
    
    @Test
    public void testTwoElements() {
    	String[] elements = new String[]{"hello","world"};
        ArrayIterator<String> ai = new ArrayIterator<String>(elements);
        assertTrue(ai.hasNext());
        assertEquals(ai.next(),"hello");
        assertTrue(ai.hasNext());
        assertEquals(ai.next(),"world");
        assertFalse(ai.hasNext());
    }
   
    @Test
    public void testTwoElementsNoReadAhead() {
    	String[] elements = new String[]{"hello","world"};
        ArrayIterator<String> ai = new ArrayIterator<String>(elements);
        assertEquals(ai.next(),"hello");
        assertEquals(ai.next(),"world");
        assertFalse(ai.hasNext());
    }

    @Test
    public void testTwoElementsPeek() {
    	String[] elements = new String[]{"hello","world"};
        ArrayIterator<String> ai = new ArrayIterator<String>(elements);
        assertEquals(ai.peek(),"hello");
        assertEquals(ai.next(),"hello");
        assertEquals(ai.peek(),"world");
        assertEquals(ai.next(),"world");
        assertFalse(ai.hasNext());
    }

    
    @Test(expected=java.util.NoSuchElementException.class)
    public void testReadPastEmpty() {
        String[] empty = new String[0];
        ArrayIterator<String> ai = new ArrayIterator<String>(empty);
        ai.next();
    }

    @Test(expected=java.util.NoSuchElementException.class)
    public void testPeekPastEmpty() {
        String[] empty = new String[0];
        ArrayIterator<String> ai = new ArrayIterator<String>(empty);
        ai.peek();
    }
    
    @Test(expected=java.util.NoSuchElementException.class)
    public void testReadPastTwo() {
    	String[] elements = new String[]{"hello","world"};
        ArrayIterator<String> ai = new ArrayIterator<String>(elements);
        assertEquals(ai.next(),"hello");
        assertEquals(ai.next(),"world");
        ai.next();
    }
    
    

}
