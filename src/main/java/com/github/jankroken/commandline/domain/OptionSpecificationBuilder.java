package com.github.jankroken.commandline.domain;

import java.lang.reflect.Method;

public class OptionSpecificationBuilder {

    private String longSwitch;
    private String shortSwitch;
    private final ArgumentConsumptionBuilder argumentConsumptionBuilder = new ArgumentConsumptionBuilder();
    private Method method;
    private Occurrences occurrences = Occurrences.SINGLE;
    private boolean required;
    private Object spec;

    public void addMethod(Method method) {
        this.method = method;
    }

    public void addLongSwitch(String longSwitch) {
        this.longSwitch = longSwitch;
    }

    public void addShortSwitch(String shortSwitch) {
        this.shortSwitch = shortSwitch;
    }

    public void addToggle(boolean value) {
        argumentConsumptionBuilder.addNoArgs(value);
    }

    public void addSingleArgument() {
        argumentConsumptionBuilder.addSingleArgument();
    }

    public void addAllAvailableArguments() {
        argumentConsumptionBuilder.addAllAvailable();
    }

    public void addUntilDelimiter(String delimiter) {
        argumentConsumptionBuilder.addUntilDelimiter(delimiter);
    }

    public void addSubset(Class<?> optionClass) {
        argumentConsumptionBuilder.addSubSet(optionClass);
    }

    public void addLooseArgs() {
        argumentConsumptionBuilder.addLooseArgs();
    }

    public void addRequired() {
        required = true;
    }

    public void addOccurrences(Occurrences occurrences) {
        this.occurrences = occurrences;
    }

    public void addConfiguration(Object spec) {
        this.spec = spec;
    }

    public OptionSpecification getOptionSpecification() {
        var _switch = new Switch(longSwitch, shortSwitch);
        var argumentConsumption = argumentConsumptionBuilder.getArgumentConsumption();
        return new OptionSpecification(spec, method, _switch, argumentConsumption, required, occurrences);
    }


}
