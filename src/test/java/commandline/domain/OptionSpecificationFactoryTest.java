package commandline.domain;

import java.util.List;

import org.junit.Test;

import commandline.SimpleConfiguration;
import commandline.domain.OptionSpecification;
import static org.junit.Assert.assertEquals;

public class OptionSpecificationFactoryTest {

	@Test
	public void testOptionSpecificationFactory() {
		SimpleConfiguration conf = new SimpleConfiguration();
		List<OptionSpecification> specifications = OptionSpecificationFactory.getOptionSpecifications(conf,conf.getClass());
		assertEquals(specifications.size(),2);
	}
}
