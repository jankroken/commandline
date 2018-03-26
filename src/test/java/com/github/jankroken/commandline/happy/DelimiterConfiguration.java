package com.github.jankroken.commandline.happy;

import com.github.jankroken.commandline.annotations.*;

import java.util.List;


public class DelimiterConfiguration {

    private List<String> command;
    private String logfile;

    public List<String> getCommand() {
        return command;
    }

    @Option
    @LongSwitch("exec")
    @ShortSwitch("e")
    @ArgumentsUntilDelimiter(";")
    public void setFilename(List<String> command) {
        this.command = command;
    }

    public String getLogfile() {
        return logfile;
    }

    @Option
    @LongSwitch("logfile")
    @ShortSwitch("l")
    @SingleArgument
    public void setLogfile(String logfile) {
        this.logfile = logfile;
    }

}
