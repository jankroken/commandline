package com.github.jankroken.commandline.error;

import com.github.jankroken.commandline.annotations.LongSwitch;
import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.annotations.SingleArgument;
import com.github.jankroken.commandline.annotations.Toggle;

public class MultipleConsumptionsConfiguration {

    @Option
    @LongSwitch("filename")
    @SingleArgument
    @Toggle(true)
    public void setFilename(String filename) {
    }

}
