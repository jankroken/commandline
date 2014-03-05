package com.github.jankroken.commandline.error;

import com.github.jankroken.commandline.annotations.LongSwitch;
import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.annotations.SingleArgument;

public class InvalidTypeConfiguration {

    @Option
    @LongSwitch("filename")
    @SingleArgument
    public void setFilename(int filename) {
    }

}
