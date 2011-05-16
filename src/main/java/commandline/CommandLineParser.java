package commandline;

import java.lang.reflect.InvocationTargetException;

import commandline.domain.OptionSet;
import commandline.domain.OptionSetLevel;
import commandline.util.ArrayIterator;
import commandline.util.PeekIterator;

public class CommandLineParser {
	
	/**
	 * A command line parser. It takes two arguments, a class and an argument list,
	 * and returns an instance of the class, populated from the argument list based
	 * on the annotations in the class. The class must have a no argument constructor.
	 * @param <T> The type of the provided class
	 * @param clazz A class that contains annotated setters that options/arguments will be assigned to
	 * @param args The provided argument list, typically the argument from the static main method
	 * @return An instance of the provided class, populated with the options and arguments of the argument list
	 * @throws IllegalAccessException May be thrown when invoking the setters in the specified class
	 * @throws InstantiationException May be thrown when creating an instance of the specified class
	 * @throws InvocationTargetException May be thrown when invoking the setters in the specified class
	 * @throws InternalErrorException This indicates an internal error in the parser, and will not normally be thrown
	 * @throws InvalidCommandLineException This indicates that the command line specified does not match the annotations in the provided class
	 * @throws InvalidOptionConfigurationException This indicates that the annotations of setters in the provided class are not valid
	 * @throws UnrecognizedSwitchException This indicates that the argument list contains a switch which is not specified by the class annotations
	 */
	public static <T extends Object> T parse(Class<T> clazz, String[] args) 
		throws IllegalAccessException, InstantiationException, InvocationTargetException
	{
		T spec = clazz.newInstance();
		OptionSet optionSet = new OptionSet(spec, OptionSetLevel.MAIN_OPTIONS);
		optionSet.consumeOptions(new PeekIterator<String>(new ArrayIterator<String>(args)));
		return spec;
	}
}