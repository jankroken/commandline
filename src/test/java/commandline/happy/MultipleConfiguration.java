package commandline.happy;

import java.util.List;

import commandline.annotations.LongSwitch;
import commandline.annotations.Multiple;
import commandline.annotations.Option;
import commandline.annotations.ShortSwitch;
import commandline.annotations.SingleArgument;
import commandline.annotations.Toggle;


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
