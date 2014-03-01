package com.github.jankroken.commandline.domain;

import com.zerolegacy.commandline.domain.OptionSpecification;
import com.zerolegacy.commandline.domain.OptionSpecificationFactory;
import com.zerolegacy.commandline.happy.SimpleConfiguration;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OptionSpecificationFactoryTest {

    @Test
    public void testOptionSpecificationFactory() {
        SimpleConfiguration conf = new SimpleConfiguration();
        List<OptionSpecification> specifications = OptionSpecificationFactory.getOptionSpecifications(conf, conf.getClass());
        assertEquals(specifications.size(), 2);
    }
}
