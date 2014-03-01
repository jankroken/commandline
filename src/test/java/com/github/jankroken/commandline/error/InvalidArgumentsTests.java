package com.github.jankroken.commandline.error;

import com.zerolegacy.commandline.CommandLineParser;
import com.zerolegacy.commandline.OptionStyle;
import com.zerolegacy.commandline.domain.InvalidCommandLineException;
import com.zerolegacy.commandline.domain.UnrecognizedSwitchException;
import com.zerolegacy.commandline.error.RequiredConfiguration;
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