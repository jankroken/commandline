package com.github.jankroken.commandline.domain;

public abstract class CommandLineException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected CommandLineException() {
    }

    protected CommandLineException(String message) {
        super(message);
    }

    protected CommandLineException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CommandLineException(Throwable cause) {
        super(cause);
    }

    protected CommandLineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
