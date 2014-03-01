package com.github.jankroken.commandline.domain;

import com.zerolegacy.commandline.domain.LongOrCompactTokenizer;
import com.zerolegacy.commandline.util.ArrayIterator;
import com.zerolegacy.commandline.util.PeekIterator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LongOrCompactTokenizerTest {

    @Test
    public void simpleArgumentSplit() {
        String[] args = new String[]{"-abcf", "hello.txt"};
        PeekIterator<String> peekIterator = new PeekIterator<String>(new ArrayIterator<String>(args));
        LongOrCompactTokenizer tokenizer = new LongOrCompactTokenizer(peekIterator);
        assertThat(tokenizer.next().getValue(), is("a"));
        assertThat(tokenizer.next().getValue(), is("b"));
        assertThat(tokenizer.next().getValue(), is("c"));
        assertThat(tokenizer.next().getValue(), is("f"));
        assertThat(tokenizer.next().getValue(), is("hello.txt"));
    }
}
