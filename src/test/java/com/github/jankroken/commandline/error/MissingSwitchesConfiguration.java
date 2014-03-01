package com.github.jankroken.commandline.error;

import com.zerolegacy.commandline.annotations.Option;
import com.zerolegacy.commandline.annotations.SingleArgument;

public class MissingSwitchesConfiguration {

    @Option
    @SingleArgument
    public void setFilename(String filename) {
    }

}
