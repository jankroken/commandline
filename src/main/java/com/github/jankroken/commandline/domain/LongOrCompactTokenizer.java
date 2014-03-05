package com.github.jankroken.commandline.domain;

import com.github.jankroken.commandline.util.PeekIterator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LongOrCompactTokenizer implements Tokenizer {

    private PeekIterator<String> stringIterator;
    private boolean argumentEscapeEncountered = false;
    private String argumentTerminator = null;
    private LinkedList<SwitchToken> splitTokens = new LinkedList<SwitchToken>();

    public LongOrCompactTokenizer(PeekIterator<String> stringIterator) {
        this.stringIterator = stringIterator;
    }

    public void setArgumentTerminator(String argumentTerminator) {
        this.argumentTerminator = argumentTerminator;
    }

    public boolean hasNext() {
        return stringIterator.hasNext();
    }

    // this is a mess - need to be cleaned up
    public Token peek() {
        if (!splitTokens.isEmpty()) {
            return splitTokens.peek();
        }
        String value = stringIterator.peek();

        if (argumentEscapeEncountered) {
            return new ArgumentToken(value);
        }

        if (value.equals(argumentTerminator)) {
            return new ArgumentToken(value);
        }
        if (argumentTerminator != null) {
            return new ArgumentToken(value);
        }
        if (isArgumentEscape(value)) {
            argumentEscapeEncountered = true;
            return peek();
        }
        if (isSwitch(value)) {
            if (isShortSwitchList(value)) {
                List<SwitchToken> tokens = splitSwitchTokens(value);
                return tokens.get(0);
            } else {
                return new SwitchToken(value.substring(2), value);
            }
        } else {
            return new ArgumentToken(value);
        }
    }

    // this is a mess - needs to be cleaned up
    public Token next() {
        if (!splitTokens.isEmpty()) {
            return splitTokens.remove();
        }
        String value = stringIterator.next();

        if (argumentEscapeEncountered) {
            return new ArgumentToken(value);
        }

        if (value.equals(argumentTerminator)) {
            argumentTerminator = null;
            return new ArgumentToken(value);
        }
        if (argumentTerminator != null) {
            return new ArgumentToken(value);
        }
        if (isArgumentEscape(value)) {
            argumentEscapeEncountered = true;
            return next();
        }
        if (isSwitch(value)) {
            if (isShortSwitchList(value)) {
                splitTokens.addAll(splitSwitchTokens(value));
                return splitTokens.remove();
            } else {
                return new SwitchToken(value.substring(2), value);
            }
        } else {
            return new ArgumentToken(value);
        }
    }

    public void remove() {
        stringIterator.remove();
    }

    private boolean isSwitch(String argument) {
        return argument.matches("-.*");
    }

    private boolean isArgumentEscape(String value) {
        return ("--".equals(value) && !argumentEscapeEncountered);
    }

    private boolean isLongSwitch(String value) {
        return value.matches("--..*");
    }

    private boolean isShortSwitchList(String value) {
        return value.matches("-[^-].*");
    }

    private List<SwitchToken> splitSwitchTokens(String value) {
        ArrayList<SwitchToken> tokens = new ArrayList<>();
        for (int i = 1; i < value.length(); i++) {
            tokens.add(new SwitchToken(String.valueOf(value.charAt(i)), value));
        }
        return tokens;
    }

}