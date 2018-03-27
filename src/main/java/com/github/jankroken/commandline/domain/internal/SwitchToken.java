package com.github.jankroken.commandline.domain.internal;

public class SwitchToken implements Token {
    private final String value;
    private final String argumentValue;

    public SwitchToken(String value, String argumentValue) {
        this.value = value;
        this.argumentValue = argumentValue;
    }

    public String getValue() {
        return value;
    }

    public String getArgumentValue() {
        return argumentValue;
    }

    public String toString() {
        return "<SwitchToken:" + value + "(" + argumentValue + ")>";
    }
}
