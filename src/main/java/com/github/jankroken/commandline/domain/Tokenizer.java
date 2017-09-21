package com.github.jankroken.commandline.domain;

import java.util.Iterator;

public interface Tokenizer extends Iterator<Token> {

    void setArgumentTerminator(String argumentTerminator);

    boolean hasNext();

    Token peek();

    Token next();

    void remove();
}