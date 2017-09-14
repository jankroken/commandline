package com.github.jankroken.commandline.error;

import com.github.jankroken.commandline.domain.InvalidOptionConfigurationException;
import org.junit.jupiter.api.Test;

import static com.github.jankroken.commandline.CommandLineParser.parse;
import static com.github.jankroken.commandline.OptionStyle.SIMPLE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class InvalidConfigurationTests {

    private String[] EMPTY_ARGS = new String[]{};


    @Test
    public void testMissingSwitches() throws Exception {
        assertThatThrownBy(() -> parse(MissingSwitchesConfiguration.class, EMPTY_ARGS, SIMPLE))
                .isInstanceOf(InvalidOptionConfigurationException.class);
    }

    @Test
    public void testMissingArgumentConsumption() throws Exception {
        assertThatThrownBy(() -> parse(MissingArgumentConsumptionConfiguration.class, EMPTY_ARGS, SIMPLE))
                .isInstanceOf(InvalidOptionConfigurationException.class);
    }

    @Test
    public void testMultipleArgumentConsumptions() throws Exception {
        assertThatThrownBy(() -> parse(MultipleConsumptionsConfiguration.class, EMPTY_ARGS, SIMPLE))
                .isInstanceOf(InvalidOptionConfigurationException.class);
    }

    @Test
    public void testInvalidType() throws Exception {
        String[] args = new String[]{"-filename", "hello.txt"};
        assertThatThrownBy(() -> parse(InvalidTypeConfiguration.class, args, SIMPLE))
                .isInstanceOf(InvalidOptionConfigurationException.class);
    }

}