package commandline.error;

import java.util.List;

import org.junit.Test;

import commandline.CommandLineParser;
import commandline.domain.InvalidCommandLineException;
import commandline.domain.InvalidOptionSpecificationException;
import commandline.happy.AlbumConfiguration;
import commandline.happy.DelimiterConfiguration;
import commandline.happy.MultipleArgsConfiguration;
import commandline.happy.MultipleConfiguration;
import commandline.happy.MultipleSubconfigsConfiguration;
import commandline.happy.SimpleConfiguration;
import commandline.happy.SimpleSuperConfiguration;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

public class InvalidArgumentsTests {

	@Test(expected=InvalidCommandLineException.class)
	public void testMissingSwitches() throws Exception {
		String[] args = new String[]{};
		RequiredConfiguration config = CommandLineParser.parse(RequiredConfiguration.class, args);
	}
	

}