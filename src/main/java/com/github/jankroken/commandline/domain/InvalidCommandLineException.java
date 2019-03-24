package com.github.jankroken.commandline.domain;

public class InvalidCommandLineException extends CommandLineException {
    private static final long serialVersionUID = 2L;

    public InvalidCommandLineException(String message) {
        super(message);
    }
}
