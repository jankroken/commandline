package com.github.jankroken.commandline.domain;

import com.github.jankroken.commandline.util.ArrayIterator;
import com.github.jankroken.commandline.util.PeekIterator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LongOrCompactTokenizerTest {

    @Test
    public void simpleArgumentSplit() {
        var args = new String[]{"-abcf", "hello.txt"};
        var peekIterator = new PeekIterator<>(new ArrayIterator<>(args));
        var tokenizer = new LongOrCompactTokenizer(peekIterator);
        assertThat(tokenizer.next().getValue()).isEqualTo("a");
        assertThat(tokenizer.next().getValue()).isEqualTo("b");
        assertThat(tokenizer.next().getValue()).isEqualTo("c");
        assertThat(tokenizer.next().getValue()).isEqualTo("f");
        assertThat(tokenizer.next().getValue()).isEqualTo("hello.txt");
    }
}
