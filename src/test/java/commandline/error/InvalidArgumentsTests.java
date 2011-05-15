package commandline.error;

import org.junit.Test;

import commandline.CommandLineParser;
import commandline.domain.InvalidCommandLineException;

public class InvalidArgumentsTests {

	@Test(expected=InvalidCommandLineException.class)
	public void testMissingSwitches() throws Exception {
		String[] args = new String[]{};
		RequiredConfiguration config = CommandLineParser.parse(RequiredConfiguration.class, args);
	}

}