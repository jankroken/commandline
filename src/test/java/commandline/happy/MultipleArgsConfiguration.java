package commandline.happy;

import java.util.List;

import commandline.annotations.AllAvailableArguments;
import commandline.annotations.LongSwitch;
import commandline.annotations.Option;
import commandline.annotations.ShortSwitch;
import commandline.annotations.SingleArgument;

public class MultipleArgsConfiguration {
	
	private List<String> files;
	private String logfile;

	public List<String> getFiles() {
		return files;
	}
	
	@Option
	@LongSwitch("--files")
	@ShortSwitch("-f")
	@AllAvailableArguments
	public void setFiles(List<String> files) {
		this.files = files;
	}
	public String getLogfile() {
		return logfile;
	}
	
	@Option
	@LongSwitch("--logfile")
	@ShortSwitch("-l")
	@SingleArgument
	public void setLogfile(String logfile) {
		this.logfile = logfile;
	}
	
}
