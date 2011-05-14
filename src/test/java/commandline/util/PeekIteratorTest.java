package commandline.util;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class PeekIteratorTest {

	private PeekIterator<String> createIterator(String[] args) {
		ArrayIterator<String> ai = new ArrayIterator<String>(args);
		return new PeekIterator<String>(ai);
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

    
    @Test(expected=java.util.NoSuchElementException.class)
    public void testReadPastEmpty() {
        String[] empty = new String[0];
        PeekIterator<String> ai = createIterator(empty);
        ai.next();
    }

    @Test(expected=java.util.NoSuchElementException.class)
    public void testPeekPastEmpty() {
        String[] empty = new String[0];
        PeekIterator<String> ai = createIterator(empty);
        ai.peek();
    }
    
    @Test(expected=java.util.NoSuchElementException.class)
    public void testReadPastTwo() {
    	String[] elements = new String[]{"hello","world"};
    	PeekIterator<String> ai = createIterator(elements);
        assertEquals(ai.next(),"hello");
        assertEquals(ai.next(),"world");
        ai.next();
    }

}
