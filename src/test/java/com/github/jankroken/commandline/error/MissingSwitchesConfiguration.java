package com.github.jankroken.commandline.error;

import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.annotations.SingleArgument;

public class MissingSwitchesConfiguration {

    @Option
    @SingleArgument
    public void setFilename(String filename) {
    }

}
