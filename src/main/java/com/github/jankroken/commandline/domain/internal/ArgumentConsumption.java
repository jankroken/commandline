package com.github.jankroken.commandline.domain.internal;

public class ArgumentConsumption {
    private final ArgumentConsumptionType type;
    private String delimiter;
    private boolean toggleValue;
    private Class<?> subsetClass;

    public ArgumentConsumption(ArgumentConsumptionType type) {
        this.type = type;
    }

    public ArgumentConsumption(ArgumentConsumptionType type, String delimiter) {
        this.type = type;
        this.delimiter = delimiter;
    }

    public ArgumentConsumption(ArgumentConsumptionType type, boolean toggleValue) {
        this.type = type;
        this.toggleValue = toggleValue;
    }

    public ArgumentConsumption(ArgumentConsumptionType type, Class<?> subsetClass) {
        this.type = type;
        this.subsetClass = subsetClass;
    }

    public ArgumentConsumptionType getType() {
        return type;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public Class<?> getSubsetClass() {
        return subsetClass;
    }

    public boolean getToggleValue() {
        return toggleValue;
    }
}