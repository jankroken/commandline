package commandline.error;

import commandline.annotations.Option;
import commandline.annotations.SingleArgument;

public class MissingSwitchesConfiguration {

    @Option
    @SingleArgument
    public void setFilename(String filename) {
    }

}
