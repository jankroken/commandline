package com.github.jankroken.commandline.happy;

import java.util.List;

import com.github.jankroken.commandline.annotations.ArgumentsUntilDelimiter;
import com.github.jankroken.commandline.annotations.LongSwitch;
import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.annotations.ShortSwitch;
import com.github.jankroken.commandline.annotations.SingleArgument;


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
