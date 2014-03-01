package com.github.jankroken.commandline.domain;

public class InvalidCommandLineException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidCommandLineException(String message) {
        super(message);
    }
}
