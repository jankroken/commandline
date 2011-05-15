package commandline.error;

import commandline.annotations.LongSwitch;
import commandline.annotations.Option;
import commandline.annotations.Required;
import commandline.annotations.SingleArgument;

public class InvalidTypeConfiguration {
	
	@Option
	@LongSwitch("--filename")
	@SingleArgument
	public void setFilename(int filename) {
	}
	
}
