package com.github.jankroken.commandline.domain;

import com.github.jankroken.commandline.domain.internal.Token;

public class ArgumentToken implements Token {
    private final String value;

    public ArgumentToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getArgumentValue() {
        return value;
    }

    @Override
    public String toString() {
        return "<ArgumentToken:" + value + ">";
    }
}
