package com.github.jankroken.commandline;

import com.github.jankroken.commandline.domain.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Function;

import static com.github.jankroken.commandline.ParseResult.ErrorType.*;

public class ParseResult<T> {
    public enum ErrorType {
        OK,
        INTERNAL_ERROR,
        INVALID_COMMAND_LINE,
        INVALID_OPTION_CONFIGURATION,
        UNRECOGNIZED_SWITCH,
        INVALID_CONFIGURATION
    }

    private final ErrorType errorType;
    private final boolean success;
    private final T value;
    private final Exception rootCause;
    private final String errorMessage;

    protected ParseResult(Exception e, boolean success, T value) {
        errorType = success ? OK : toErrorType(e);
        rootCause = e;
        errorMessage = e == null ? null : e.getMessage();
        this.success = success;
        this.value = value;
    }

    protected ParseResult(Exception t) {
        this(t, false, null);
    }

    protected ParseResult(T value) {
        this(null, true, value);
    }

    public Optional<T> toOptional() {
        if (success) return Optional.of(value);
        else return Optional.empty();
    }

    public T get() throws CommandLineWrappedException {
        if (success) return value;
        throw new CommandLineWrappedException(rootCause);
    }

    public <R> ParseResult<R> map(Function<T, R> f) {
        if (success) {
            return new ParseResult(f.apply(value));
        } else {
            return new ParseResult(rootCause);
        }
    }

    private static ErrorType toErrorType(Exception e) {
        if (e instanceof InternalErrorException) return INTERNAL_ERROR;
        if (e instanceof InvalidCommandLineException) return INVALID_COMMAND_LINE;
        if (e instanceof InvalidOptionConfigurationException) return INVALID_OPTION_CONFIGURATION;
        if (e instanceof UnrecognizedSwitchException) return UNRECOGNIZED_SWITCH;
        if (e instanceof InvocationTargetException) return INVALID_CONFIGURATION;
        if (e instanceof IllegalAccessException) return INVALID_CONFIGURATION;
        if (e instanceof InstantiationException) return INVALID_CONFIGURATION;
        throw new RuntimeException("Implementation error: unhandled exception", e);
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public boolean isSuccess() {
        return success;
    }

    public Exception getRootCause() {
        return rootCause;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}