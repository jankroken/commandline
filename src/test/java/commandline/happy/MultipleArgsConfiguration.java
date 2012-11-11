package commandline.happy;

import commandline.annotations.*;

import java.util.List;


public class MultipleArgsConfiguration {

    private List<String> files;
    private String logfile;

    public List<String> getFiles() {
        return files;
    }

    @Option
    @LongSwitch("files")
    @ShortSwitch("f")
    @AllAvailableArguments
    public void setFiles(List<String> files) {
        this.files = files;
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
