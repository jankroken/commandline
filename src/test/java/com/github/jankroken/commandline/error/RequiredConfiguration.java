package com.github.jankroken.commandline.error;

import com.zerolegacy.commandline.annotations.LongSwitch;
import com.zerolegacy.commandline.annotations.Option;
import com.zerolegacy.commandline.annotations.Required;
import com.zerolegacy.commandline.annotations.SingleArgument;

public class RequiredConfiguration {

    @Option
    @LongSwitch("filename")
    @SingleArgument
    @Required
    public void setFilename(String filename) {
    }

}
