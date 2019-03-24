package com.github.jankroken.commandline.domain;

public class InvalidOptionConfigurationException extends CommandLineException {
    private static final long serialVersionUID = 2L;

    public InvalidOptionConfigurationException(String message) {
        super(message);
    }
}
