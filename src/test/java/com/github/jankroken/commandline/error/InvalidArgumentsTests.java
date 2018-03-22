package com.github.jankroken.commandline.error;

import com.github.jankroken.commandline.domain.InvalidCommandLineException;
import com.github.jankroken.commandline.domain.UnrecognizedSwitchException;
import org.junit.jupiter.api.Test;

import static com.github.jankroken.commandline.CommandLineParser.parse;
import static com.github.jankroken.commandline.OptionStyle.SIMPLE;
import static com.github.jankroken.commandline.util.Constants.EMPTY_STRING_ARRAY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class InvalidArgumentsTests {

    @Test
    public void testMissingSwitches() {
        var args = EMPTY_STRING_ARRAY;
        assertThatThrownBy(() -> parse(RequiredConfiguration.class, args, SIMPLE))
                .isInstanceOf(InvalidCommandLineException.class);
    }

    @Test
    public void testUnrecognizedSwitch() {
        var args = new String[]{"-invalidswitch", "-filename", "hello.txt"};
        assertThatThrownBy(() -> parse(RequiredConfiguration.class, args, SIMPLE))
                .isInstanceOf(UnrecognizedSwitchException.class);
    }

}