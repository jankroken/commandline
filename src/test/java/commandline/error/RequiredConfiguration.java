package commandline.error;

import commandline.annotations.LongSwitch;
import commandline.annotations.Option;
import commandline.annotations.Required;
import commandline.annotations.SingleArgument;

public class RequiredConfiguration {
	
	@Option
	@LongSwitch("filename")
	@SingleArgument
	@Required
	public void setFilename(String filename) {
	}
	
}
