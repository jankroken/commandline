package commandline.error;

import org.junit.Test;

import commandline.CommandLineParser;
import commandline.domain.InvalidCommandLineException;
import commandline.domain.UnrecognizedSwitchException;


public class InvalidArgumentsTests {

	@Test(expected=InvalidCommandLineException.class)
	public void testMissingSwitches() throws Exception {
		String[] args = new String[]{};
		RequiredConfiguration config = CommandLineParser.parse(RequiredConfiguration.class, args);
	}
	
	@Test(expected=UnrecognizedSwitchException.class) 
	public void testUnrecognizedSwitch() throws Exception
	{
		String[] args = new String[]{"--invalidswitch","--filename","hello.txt"};
		RequiredConfiguration config = CommandLineParser.parse(RequiredConfiguration.class, args);
	}

}