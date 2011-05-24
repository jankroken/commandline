package commandline.happy;

import commandline.annotations.LongSwitch;
import commandline.annotations.Option;
import commandline.annotations.ShortSwitch;
import commandline.annotations.SingleArgument;
import commandline.annotations.Toggle;

public class SimpleConfiguration {
	
	private String filename;
	private boolean verbose;

	public String getFilename() {
		return filename;
	}
	
	@Option
	@LongSwitch("filename")
	@ShortSwitch("f")
	@SingleArgument
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public boolean getVerbose() {
		return verbose;
	}
	
	@Option
	@LongSwitch("verbose")
	@ShortSwitch("v")
	@Toggle(true)
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
}
