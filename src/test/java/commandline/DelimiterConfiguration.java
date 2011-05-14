package commandline;

import java.util.List;

import commandline.annotations.ArgumentsUntilDelimiter;
import commandline.annotations.LongSwitch;
import commandline.annotations.Option;
import commandline.annotations.ShortSwitch;
import commandline.annotations.SingleArgument;

public class DelimiterConfiguration {
	
	private List<String> command;
	private String logfile;

	public List<String> getCommand() {
		return command;
	}
	
	@Option
	@LongSwitch("--exec")
	@ShortSwitch("-e")
	@ArgumentsUntilDelimiter(";")
	public void setFilename(List<String> command) {
		this.command = command;
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
