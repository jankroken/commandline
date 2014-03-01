package com.github.jankroken.commandline.error;

import com.zerolegacy.commandline.annotations.LongSwitch;
import com.zerolegacy.commandline.annotations.Option;

public class MissingArgumentConsumptionConfiguration {

    @Option
    @LongSwitch("filename")
    public void setFilename(String filename) {
    }

}
