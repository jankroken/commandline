package commandline.happy;

import commandline.annotations.*;

import java.util.List;


public class LooseArgsConfiguration {

    private String logfile;
    private boolean verbose;
    private List<String> args;


    @Option
    @LongSwitch("logfile")
    @ShortSwitch("l")
    @SingleArgument
    public void setFilename(String logfile) {
        this.logfile = logfile;
    }

    @Option
    @LongSwitch("verbose")
    @ShortSwitch("v")
    @Toggle(true)
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    @Option
    @LooseArguments
    @Multiple
    public void setArgs(List<String> args) {
        this.args = args;
    }

    public String getLogfile() {
        return logfile;
    }

    public boolean getVerbose() {
        return verbose;
    }

    public List<String> getArgs() {
        return args;
    }
}
