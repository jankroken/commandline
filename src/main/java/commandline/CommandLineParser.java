package commandline;

import java.lang.reflect.InvocationTargetException;

import commandline.domain.OptionSet;
import commandline.domain.OptionSetLevel;
import commandline.util.ArrayIterator;
import commandline.util.PeekIterator;

public class CommandLineParser {
	public static <T extends Object> T parse(Class<T> clazz, String[] args) 
		throws IllegalAccessException, InstantiationException, InvocationTargetException
	{
		T spec = clazz.newInstance();
		OptionSet optionSet = new OptionSet(spec, OptionSetLevel.MAIN_OPTIONS);
		optionSet.consumeOptions(new PeekIterator<String>(new ArrayIterator<String>(args)));
		return spec;
	}
}