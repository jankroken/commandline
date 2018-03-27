package com.github.jankroken.commandline;

import com.github.jankroken.commandline.domain.internal.LongOrCompactTokenizer;
import com.github.jankroken.commandline.domain.internal.OptionSet;
import com.github.jankroken.commandline.domain.internal.SimpleTokenizer;
import com.github.jankroken.commandline.domain.internal.Tokenizer;
import com.github.jankroken.commandline.util.ArrayIterator;
import com.github.jankroken.commandline.util.PeekIterator;

import java.lang.reflect.InvocationTargetException;

import static com.github.jankroken.commandline.OptionStyle.SIMPLE;
import static com.github.jankroken.commandline.domain.internal.OptionSetLevel.MAIN_OPTIONS;


public class CommandLineParser {

    /**
     * A command line parser. It takes two arguments, a class and an argument list,
     * and returns an instance of the class, populated from the argument list based
     * on the annotations in the class. The class must have a no argument constructor.
     *
     * @param <T>         The type of the provided class
     * @param optionClass A class that contains annotated setters that options/arguments will be assigned to
     * @param args        The provided argument list, typically the argument from the static main method
     * @return An instance of the provided class, populated with the options and arguments of the argument list
     * @throws IllegalAccessException                                                      May be thrown when invoking the setters in the specified class
     * @throws InstantiationException                                                      May be thrown when creating an instance of the specified class
     * @throws InvocationTargetException                                                   May be thrown when invoking the setters in the specified class
     * @throws com.github.jankroken.commandline.domain.InternalErrorException              This indicates an internal error in the parser, and will not normally be thrown
     * @throws com.github.jankroken.commandline.domain.InvalidCommandLineException         This indicates that the command line specified does not match the annotations in the provided class
     * @throws com.github.jankroken.commandline.domain.InvalidOptionConfigurationException This indicates that the annotations of setters in the provided class are not valid
     * @throws com.github.jankroken.commandline.domain.UnrecognizedSwitchException         This indicates that the argument list contains a switch which is not specified by the class annotations
     */
    public static <T> T parse(Class<T> optionClass, String[] args, OptionStyle style)
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T spec;
        try {
            spec = optionClass.getConstructor().newInstance();
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException(noSuchMethodException);
        }
        var optionSet = new OptionSet(spec, MAIN_OPTIONS);
        Tokenizer tokenizer;
        if (style == SIMPLE) {
            tokenizer = new SimpleTokenizer(new PeekIterator<>(new ArrayIterator<>(args)));
        } else {
            tokenizer = new LongOrCompactTokenizer(new PeekIterator<>(new ArrayIterator<>(args)));
        }
        optionSet.consumeOptions(tokenizer);
        return spec;
    }
}