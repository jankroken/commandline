package com.github.jankroken.commandline.domain;

public class InternalErrorException extends CommandLineException {
    private static final long serialVersionUID = 2L;

    public InternalErrorException(String message) {
        super(message);
    }
}
