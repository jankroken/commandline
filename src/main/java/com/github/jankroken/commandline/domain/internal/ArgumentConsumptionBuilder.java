package com.github.jankroken.commandline.domain.internal;

import com.github.jankroken.commandline.domain.InternalErrorException;
import com.github.jankroken.commandline.domain.InvalidOptionConfigurationException;

import static com.github.jankroken.commandline.domain.internal.ArgumentConsumptionType.*;

public class ArgumentConsumptionBuilder {
    private boolean no_arguments = false;
    private boolean toggle_value = false;
    private boolean single_argument = false;
    private boolean until_delimiter = false;
    private boolean all_available = false;
    private boolean sub_set = false;
    private boolean loose_args = false;
    private String delimiter = null;
    private Class<?> subsetClass = null;

    public void addNoArgs(boolean value) {
        no_arguments = true;
        toggle_value = value;
    }

    public void addSingleArgument() {
        single_argument = true;
    }

    public void addUntilDelimiter(String delimiter) {
        until_delimiter = true;
        this.delimiter = delimiter;
    }

    public void addAllAvailable() {
        all_available = true;
    }

    public void addLooseArgs() {
        loose_args = true;
    }

    public void addSubSet(Class<?> subsetClass) {
        this.sub_set = true;
        this.subsetClass = subsetClass;
    }

    public ArgumentConsumption getArgumentConsumption() {
        var argumentConsumptionTypeCounter = 0;
        if (no_arguments) argumentConsumptionTypeCounter++;
        if (single_argument) argumentConsumptionTypeCounter++;
        if (until_delimiter) argumentConsumptionTypeCounter++;
        if (all_available) argumentConsumptionTypeCounter++;
        if (sub_set) argumentConsumptionTypeCounter++;
        if (loose_args) argumentConsumptionTypeCounter++;
        if (argumentConsumptionTypeCounter == 0) {
            throw new InvalidOptionConfigurationException("No argument consumption type specified");
        }
        if (argumentConsumptionTypeCounter > 1) {
            throw new InvalidOptionConfigurationException("Multiple argument consumption types specified");
        }

        if (no_arguments) {
            return new ArgumentConsumption(NO_ARGS, toggle_value);
        }
        if (single_argument) {
            return new ArgumentConsumption(SINGLE_ARGUMENT);
        }
        if (until_delimiter) {
            return new ArgumentConsumption(UNTIL_DELIMITER, delimiter);
        }
        if (all_available) {
            return new ArgumentConsumption(ALL_AVAILABLE);
        }
        if (sub_set) {
            return new ArgumentConsumption(SUB_SET, subsetClass);
        }
        if (loose_args) {
            return new ArgumentConsumption(LOOSE_ARGS);
        }
        throw new InternalErrorException("Internal error: no matching argument consumption types");
    }

}