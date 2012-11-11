package commandline.error;

import commandline.annotations.LongSwitch;
import commandline.annotations.Option;
import commandline.annotations.SingleArgument;
import commandline.annotations.Toggle;

public class MultipleConsumptionsConfiguration {

    @Option
    @LongSwitch("filename")
    @SingleArgument
    @Toggle(true)
    public void setFilename(String filename) {
    }

}
