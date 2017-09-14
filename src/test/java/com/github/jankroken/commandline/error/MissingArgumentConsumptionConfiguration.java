package com.github.jankroken.commandline.error;

import com.github.jankroken.commandline.annotations.LongSwitch;
import com.github.jankroken.commandline.annotations.Option;

public class MissingArgumentConsumptionConfiguration {

    @Option
    @LongSwitch("filename")
    public void setFilename(@SuppressWarnings("unused") String filename) {
    }

}
