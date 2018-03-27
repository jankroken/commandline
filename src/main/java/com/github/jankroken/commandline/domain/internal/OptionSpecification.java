package com.github.jankroken.commandline.domain.internal;

import com.github.jankroken.commandline.domain.InternalErrorException;
import com.github.jankroken.commandline.domain.InvalidCommandLineException;
import com.github.jankroken.commandline.domain.InvalidOptionConfigurationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OptionSpecification {
    private final Method method;
    private boolean activated;
    private final Switch _switch;
    private final ArgumentConsumption argumentConsumption;
    private final boolean required;
    private final Occurrences occurrences;
    private final Object spec;
    private final ArrayList<Object> argumentBuffer = new ArrayList<>();

    public OptionSpecification(Object spec, Method method, Switch _switch, ArgumentConsumption argumentConsumption, boolean required, Occurrences occurrences) {
        this.spec = spec;
        this._switch = _switch;
        this.method = method;
        this.argumentConsumption = argumentConsumption;
        this.required = required;
        this.occurrences = occurrences;
        validate();
    }

    public boolean isLooseArgumentsSpecification() {
        return argumentConsumption.getType() == ArgumentConsumptionType.LOOSE_ARGS;
    }

    public void validate() {
        if (argumentConsumption.getType() != ArgumentConsumptionType.LOOSE_ARGS) {
            if (_switch == null || (_switch.getShortSwitch() == null && _switch.getLongSwitch() == null)) {
                throw createInvalidOptionSpecificationException("Option specified without switches");
            }
        }
        validateType();
    }

    private void validateType() {
        var types = method.getGenericParameterTypes();
        if (types == null || types.length != 1) {
            throw createInvalidOptionSpecificationException("Wrong number of arguments, expecting exactly one");
        }
        var type = types[0];
        var listLevel = getListLevel();
        verifyType(listLevel, getInnerArgumentType(), type, type);
    }

    @SuppressWarnings("unchecked")
    private void verifyType(int listLevel, Class<?> innerClass, Type type, Type fullType) {
        if (listLevel > 0) {
            if (!((toClass(type)).isAssignableFrom(List.class))) {
                wrongType(fullType);
            } else {
                var innerType = ((ParameterizedType) type).getActualTypeArguments()[0];
                verifyType(listLevel - 1, innerClass, innerType, fullType);
            }
        } else {
            if (!box(toClass(type)).isAssignableFrom(innerClass)) {
                wrongType(fullType);
            }
        }
    }

    private Class<?> toClass(Type type) {
        if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else if (type instanceof Class) {
            return (Class<?>) type;
        } else {
            throw createInternalErrorException("Don't know how to get the class from type " + type);
        }
    }

    @SuppressWarnings("unchecked")
    private static Class<?> box(Class<?> rawClass) {
        if (rawClass == boolean.class) {
            return Boolean.class;
        } else {
            return rawClass;
        }
    }

    private void wrongType(Type type) {
        throw createInvalidOptionSpecificationException("Wrong argument type, expected " + getExpectedTypeDescription() + " but found " + type);
    }

    private String getExpectedTypeDescription() {
        var sb = new StringBuilder();
        final var listLevel = getListLevel();
        for (var i = 0; i < listLevel; i++) {
            sb.append("List<");
        }
        sb.append(getInnerArgumentType());
        for (var i = 0; i < listLevel; i++) {
            sb.append(">");
        }
        return sb.toString();
    }

    private int getListLevel() {
        var listLevel = 0;
        if (occurrences == Occurrences.MULTIPLE) {
            listLevel++;
        }
        switch (argumentConsumption.getType()) {
            case NO_ARGS:
            case SINGLE_ARGUMENT:
            case SUB_SET:
                break;
            case ALL_AVAILABLE:
            case UNTIL_DELIMITER:
                listLevel++;
                break;
        }
        return listLevel;
    }

    private Class<?> getInnerArgumentType() {
        switch (argumentConsumption.getType()) {
            case NO_ARGS:
                return Boolean.class;
            case SINGLE_ARGUMENT:
                return String.class;
            case SUB_SET:
                return argumentConsumption.getSubsetClass();
            case ALL_AVAILABLE:
                return String.class;
            case UNTIL_DELIMITER:
                return String.class;
            case LOOSE_ARGS:
                return String.class;
            default:
                throw new RuntimeException("Internal error");
        }
    }

    public Switch getSwitch() {
        return _switch;
    }

    public void activateAndConsumeArguments(Tokenizer args)
            throws InvocationTargetException, IllegalAccessException, InstantiationException {
        activated = true;
        switch (argumentConsumption.getType()) {
            case NO_ARGS:
                handleArguments(argumentConsumption.getToggleValue());
                break;
            case SINGLE_ARGUMENT:
                if (!args.hasNext() || args.peek() instanceof SwitchToken) {
                    throw createInvalidCommandLineException("Missing argument");
                }
                final var argument = args.next().getValue();
                handleArguments(argument);
                break;
            case ALL_AVAILABLE:
                final ArrayList<String> allArguments = new ArrayList<>();
                while (args.hasNext() && !(args.peek() instanceof SwitchToken)) {
                    allArguments.add(args.next().getValue());
                }
                handleArguments(allArguments);
                break;
            case UNTIL_DELIMITER:
                args.setArgumentTerminator(argumentConsumption.getDelimiter());
                final var delimitedArguments = new ArrayList<String>();
                while (args.hasNext() && !Objects.equals(args.peek().getValue(), argumentConsumption.getDelimiter())) {
                    delimitedArguments.add(args.next().getArgumentValue());
                }
                if (args.hasNext()) args.next();
                handleArguments(delimitedArguments);
                break;
            case SUB_SET:
                final Object subset;
                try {
                    subset = argumentConsumption.getSubsetClass().getConstructor().newInstance();
                } catch (NoSuchMethodException noSuchMethodException) {
                    throw new RuntimeException(noSuchMethodException);
                }
                final var subsetOptions = new OptionSet(subset, OptionSetLevel.SUB_GROUP);
                subsetOptions.consumeOptions(args);
                handleArguments(subset);
                break;
            case LOOSE_ARGS:
                handleArguments(args.next().getValue());
                break;
            default:
                throw createInternalErrorException("Not implemented: " + argumentConsumption.getType());
        }
    }

    private void handleArguments(Object args)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (occurrences == Occurrences.SINGLE) {
            method.invoke(spec, args);
        } else {
            argumentBuffer.add(args);
        }
    }


    public void flush()
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (required && !activated) {
            throw createInvalidCommandLineException("Required argument not specified");
        }
        if (occurrences == Occurrences.MULTIPLE) {
            method.invoke(spec, argumentBuffer);
        }
    }

    private InvalidOptionConfigurationException createInvalidOptionSpecificationException(String description) {
        return new InvalidOptionConfigurationException(getOptionId() + ' ' + description);
    }

    private InvalidCommandLineException createInvalidCommandLineException(String description) {
        return new InvalidCommandLineException(getOptionId() + ' ' + description);
    }

    private RuntimeException createInternalErrorException(String description) {
        return new InternalErrorException(getOptionId() + ' ' + description);
    }

    public String getOptionId() {
        return "[" +
                spec.getClass().getName() +
                ":" +
                method.getName() +
                "]";
    }

}