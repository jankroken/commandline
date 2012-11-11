package commandline.happy;

import commandline.annotations.*;

import java.util.List;


public class MultipleConfiguration {

    private List<String> files;
    private boolean verbose;


    @Option
    @LongSwitch("file")
    @ShortSwitch("f")
    @SingleArgument
    @Multiple
    public void setFiles(List<String> files) {
        this.files = files;
    }

    @Option
    @LongSwitch("verbose")
    @ShortSwitch("v")
    @Toggle(true)
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public List<String> getFiles() {
        return files;
    }

    public boolean getVerbose() {
        return verbose;
    }
}
