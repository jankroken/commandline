package com.github.jankroken.commandline.domain;

import com.github.jankroken.commandline.util.PeekIterator;

import java.util.LinkedList;

public class TokenQueue {

    private PeekIterator<String> iterator;
    private LinkedList<Token> tokens = new LinkedList<>();
    private PeekIterator<String> stringIterator;
    private boolean argumentEscapeEncountered = false;
    private String argumentTerminator = null;
    private LinkedList<SwitchToken> splitTokens = new LinkedList<>();

    public boolean hasMore() {
        return !tokens.isEmpty() && !iterator.hasNext();
    }


}
