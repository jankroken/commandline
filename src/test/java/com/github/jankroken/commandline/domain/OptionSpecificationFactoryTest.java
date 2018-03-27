package com.github.jankroken.commandline.domain;

import com.github.jankroken.commandline.domain.internal.OptionSpecificationFactory;
import com.github.jankroken.commandline.happy.SimpleConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionSpecificationFactoryTest {

    @Test
    public void testOptionSpecificationFactory() {
        var conf = new SimpleConfiguration();
        var specifications = OptionSpecificationFactory.getOptionSpecifications(conf, conf.getClass());
        assertEquals(specifications.size(), 2);
    }
}
