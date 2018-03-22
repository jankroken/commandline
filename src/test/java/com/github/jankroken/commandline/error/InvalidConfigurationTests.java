package com.github.jankroken.commandline.error;

import com.github.jankroken.commandline.domain.InvalidOptionConfigurationException;
import org.junit.jupiter.api.Test;

import static com.github.jankroken.commandline.CommandLineParser.parse;
import static com.github.jankroken.commandline.OptionStyle.SIMPLE;
import static com.github.jankroken.commandline.util.Constants.EMPTY_STRING_ARRAY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class InvalidConfigurationTests {

    @Test
    public void testMissingSwitches() {
        assertThatThrownBy(() -> parse(MissingSwitchesConfiguration.class, EMPTY_STRING_ARRAY, SIMPLE))
                .isInstanceOf(InvalidOptionConfigurationException.class);
    }

    @Test
    public void testMissingArgumentConsumption() {
        assertThatThrownBy(() -> parse(MissingArgumentConsumptionConfiguration.class, EMPTY_STRING_ARRAY, SIMPLE))
                .isInstanceOf(InvalidOptionConfigurationException.class);
    }

    @Test
    public void testMultipleArgumentConsumptions() {
        assertThatThrownBy(() -> parse(MultipleConsumptionsConfiguration.class, EMPTY_STRING_ARRAY, SIMPLE))
                .isInstanceOf(InvalidOptionConfigurationException.class);
    }

    @Test
    public void testInvalidType() {
        var args = new String[]{"-filename", "hello.txt"};
        assertThatThrownBy(() -> parse(InvalidTypeConfiguration.class, args, SIMPLE))
                .isInstanceOf(InvalidOptionConfigurationException.class);
    }

}