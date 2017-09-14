package com.github.jankroken.commandline.domain;

import com.github.jankroken.commandline.happy.SimpleConfiguration;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionSpecificationFactoryTest {

    @Test
    public void testOptionSpecificationFactory() {
        SimpleConfiguration conf = new SimpleConfiguration();
        List<OptionSpecification> specifications = OptionSpecificationFactory.getOptionSpecifications(conf, conf.getClass());
        assertEquals(specifications.size(), 2);
    }
}
