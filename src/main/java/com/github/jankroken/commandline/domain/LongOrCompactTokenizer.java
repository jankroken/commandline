package com.github.jankroken.commandline.domain;

import com.github.jankroken.commandline.util.PeekIterator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class LongOrCompactTokenizer implements Tokenizer {

    private final PeekIterator<String> stringIterator;
    private boolean argumentEscapeEncountered;
    private String argumentTerminator;
    private final LinkedList<SwitchToken> splitTokens = new LinkedList<>();

    public LongOrCompactTokenizer(final PeekIterator<String> stringIterator) {
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
        final var value = stringIterator.peek();

        if (argumentEscapeEncountered) {
            return new ArgumentToken(value);
        }

        if (Objects.equals(value, argumentTerminator)) {
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
                var tokens = splitSwitchTokens(value);
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
        var value = stringIterator.next();

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

    private boolean isSwitch(final String argument) {
        return argument.matches("-.*");
    }

    private boolean isArgumentEscape(final String value) {
        return ("--".equals(value) && !argumentEscapeEncountered);
    }

    private boolean isLongSwitch(final String value) {
        return value.matches("--..*");
    }

    private boolean isShortSwitchList(final String value) {
        return value.matches("-[^-].*");
    }

    private List<SwitchToken> splitSwitchTokens(final String value) {
        final var tokens = new ArrayList<SwitchToken>();
        for (var i = 1; i < value.length(); i++) {
            tokens.add(new SwitchToken(String.valueOf(value.charAt(i)), value));
        }
        return tokens;
    }

}