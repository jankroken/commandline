package commandline.error;

import org.junit.Test;

import commandline.CommandLineParser;
import commandline.domain.InvalidOptionSpecificationException;

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
	
	@Test(expected=InvalidOptionSpecificationException.class)
	public void testInvalidType() throws Exception {
		String[] args = new String[]{"--filename","hello.txt"};
		InvalidTypeConfiguration config = CommandLineParser.parse(InvalidTypeConfiguration.class, args);
	}

}