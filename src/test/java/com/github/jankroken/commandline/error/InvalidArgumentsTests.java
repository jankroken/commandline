package com.github.jankroken.commandline.error;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import com.github.jankroken.commandline.domain.InvalidCommandLineException;
import com.github.jankroken.commandline.domain.UnrecognizedSwitchException;
import org.junit.Test;


public class InvalidArgumentsTests {

    @Test(expected = InvalidCommandLineException.class)
    public void testMissingSwitches() throws Exception {
        String[] args = new String[]{};
        RequiredConfiguration config = CommandLineParser.parse(RequiredConfiguration.class, args, OptionStyle.SIMPLE);
    }

    @Test(expected = UnrecognizedSwitchException.class)
    public void testUnrecognizedSwitch() throws Exception {
        String[] args = new String[]{"-invalidswitch", "-filename", "hello.txt"};
        RequiredConfiguration config = CommandLineParser.parse(RequiredConfiguration.class, args, OptionStyle.SIMPLE);
    }

}