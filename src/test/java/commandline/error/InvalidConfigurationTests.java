package commandline.error;

import java.util.List;

import org.junit.Test;

import commandline.CommandLineParser;
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

public class InvalidConfigurationTests {

	@Test(expected=InvalidOptionSpecificationException.class)
	public void testMissingSwitches() throws Exception {
		String[] args = new String[]{};
		MissingSwitchesConfiguration config = CommandLineParser.parse(MissingSwitchesConfiguration.class, args);
	}
	
	@Test(expected=InvalidOptionSpecificationException.class)
	public void testMissingArgumentConsumption() throws Exception {
		String[] args = new String[]{};
		MissingArgumentConsumptionConfiguration config = CommandLineParser.parse(MissingArgumentConsumptionConfiguration.class, args);
	}
	
	@Test(expected=InvalidOptionSpecificationException.class)
	public void testMultipleArgumentConsumptions() throws Exception {
		String[] args = new String[]{};
		MultipleConsumptionsConfiguration config = CommandLineParser.parse(MultipleConsumptionsConfiguration.class, args);
	}

}