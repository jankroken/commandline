package com.github.jankroken.commandline.domain;

public class UnrecognizedSwitchException extends CommandLineException {
    private static final long serialVersionUID = 2L;
    private String _switch;

    public UnrecognizedSwitchException(Class<?> configurationClass, String _switch) {
        super(configurationClass + ": " + _switch);
        this._switch = _switch;
    }

    public String getSwitch() {
        return _switch;
    }
}
