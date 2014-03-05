package com.github.jankroken.commandline.error;

import com.github.jankroken.commandline.annotations.LongSwitch;
import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.annotations.Required;
import com.github.jankroken.commandline.annotations.SingleArgument;

public class RequiredConfiguration {

    @Option
    @LongSwitch("filename")
    @SingleArgument
    @Required
    public void setFilename(String filename) {
    }

}
