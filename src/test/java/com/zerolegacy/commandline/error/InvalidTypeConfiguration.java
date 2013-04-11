package com.zerolegacy.commandline.error;

import com.zerolegacy.commandline.annotations.LongSwitch;
import com.zerolegacy.commandline.annotations.Option;
import com.zerolegacy.commandline.annotations.SingleArgument;

public class InvalidTypeConfiguration {

    @Option
    @LongSwitch("filename")
    @SingleArgument
    public void setFilename(int filename) {
    }

}
