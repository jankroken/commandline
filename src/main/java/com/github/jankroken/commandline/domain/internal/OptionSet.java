package com.github.jankroken.commandline.domain.internal;

import com.github.jankroken.commandline.domain.InvalidCommandLineException;
import com.github.jankroken.commandline.domain.UnrecognizedSwitchException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class OptionSet {
    private final List<OptionSpecification> options;
    private final OptionSetLevel optionSetLevel;
    private final Object spec;

    public OptionSet(Object spec, OptionSetLevel optionSetLevel) {
        options = OptionSpecificationFactory.getOptionSpecifications(spec, spec.getClass());
        this.optionSetLevel = optionSetLevel;
        this.spec = spec;
    }

    public OptionSpecification getOptionSpecification(SwitchToken _switch) {
        for (final var optionSpecification : options) {
            if (optionSpecification.getSwitch().matches(_switch.getValue())) {
                return optionSpecification;
            }
        }
        return null;
    }

    public OptionSpecification getLooseArgsOptionSpecification() {
        for (final var optionSpecification : options) {
            if (optionSpecification.isLooseArgumentsSpecification()) {
                return optionSpecification;
            }
        }
        return null;
    }

    public void consumeOptions(Tokenizer args)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        while (args.hasNext()) {
            if (args.peek() instanceof SwitchToken) {
                var optionSpecification = getOptionSpecification((SwitchToken) args.peek());
                if (optionSpecification == null) {
                    switch (optionSetLevel) {
                        case MAIN_OPTIONS:
                            throw new UnrecognizedSwitchException(spec.getClass(), args.peek().getValue());
                        case SUB_GROUP:
                            validateAndConsolidate();
                            return;
                    }
                } else {
                    args.next();
                    optionSpecification.activateAndConsumeArguments(args);
                }
            } else {
                var looseArgsOptionSpecification = getLooseArgsOptionSpecification();
                if (looseArgsOptionSpecification != null) {
                    looseArgsOptionSpecification.activateAndConsumeArguments(args);
                } else {
                    switch (optionSetLevel) {
                        case MAIN_OPTIONS:
                            throw new InvalidCommandLineException("Invalid argument: " + args.peek());
                        case SUB_GROUP:
                            validateAndConsolidate();
                            return;
                    }
                }
            }
        }
        flush();
    }

    private void flush()
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        for (final var option : options) {
            option.flush();
        }
    }

    public void validateAndConsolidate() {
    }
}
