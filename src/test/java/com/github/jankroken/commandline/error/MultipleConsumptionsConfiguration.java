package com.github.jankroken.commandline.error;

import com.zerolegacy.commandline.annotations.LongSwitch;
import com.zerolegacy.commandline.annotations.Option;
import com.zerolegacy.commandline.annotations.SingleArgument;
import com.zerolegacy.commandline.annotations.Toggle;

public class MultipleConsumptionsConfiguration {

    @Option
    @LongSwitch("filename")
    @SingleArgument
    @Toggle(true)
    public void setFilename(String filename) {
    }

}
