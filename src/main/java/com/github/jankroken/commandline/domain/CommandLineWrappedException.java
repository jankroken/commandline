package com.github.jankroken.commandline.domain;

public class CommandLineWrappedException extends CommandLineException {
    public CommandLineWrappedException(Throwable cause) {
        super(cause);
    }
}
