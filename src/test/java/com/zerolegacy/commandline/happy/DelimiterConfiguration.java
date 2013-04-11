package com.zerolegacy.commandline.happy;

import java.util.List;

import com.zerolegacy.commandline.annotations.ArgumentsUntilDelimiter;
import com.zerolegacy.commandline.annotations.LongSwitch;
import com.zerolegacy.commandline.annotations.Option;
import com.zerolegacy.commandline.annotations.ShortSwitch;
import com.zerolegacy.commandline.annotations.SingleArgument;


public class DelimiterConfiguration {
	
	private List<String> command;
	private String logfile;

	public List<String> getCommand() {
		return command;
	}
	
	@Option
	@LongSwitch("exec")
	@ShortSwitch("e")
	@ArgumentsUntilDelimiter(";")
	public void setFilename(List<String> command) {
		this.command = command;
	}
	public String getLogfile() {
		return logfile;
	}
	
	@Option
	@LongSwitch("logfile")
	@ShortSwitch("l")
	@SingleArgument
	public void setLogfile(String logfile) {
		this.logfile = logfile;
	}
	
}
