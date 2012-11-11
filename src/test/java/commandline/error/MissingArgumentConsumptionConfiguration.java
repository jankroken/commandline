package commandline.error;

import commandline.annotations.LongSwitch;
import commandline.annotations.Option;

public class MissingArgumentConsumptionConfiguration {

    @Option
    @LongSwitch("filename")
    public void setFilename(String filename) {
    }

}
