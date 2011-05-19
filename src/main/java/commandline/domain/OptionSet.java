package commandline.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.jankroken.commandline.util.PeekIterator;


public class OptionSet {
	private List<OptionSpecification> options;
	private OptionSetLevel optionSetLevel;
	private Object spec;
	
	public OptionSet(Object spec, OptionSetLevel optionSetLevel) {
		options = OptionSpecificationFactory.getOptionSpecifications(spec,spec.getClass());
		this.optionSetLevel = optionSetLevel;
		this.spec = spec;
	}
	
	public OptionSpecification getOptionSpecification(String _switch) {
		for (OptionSpecification optionSpecification : options) {
			if (optionSpecification.getSwitch().matches(_switch)) {
				return optionSpecification;
			}
		}
		return null;
	}
	
	public OptionSpecification getLooseArgsOptionSpecification() {
		for (OptionSpecification optionSpecification : options) {
			if (optionSpecification.isLooseArgumentsSpecification()) {
				return optionSpecification;
			}
		}
		return null;
	}
	
	public void consumeOptions(PeekIterator<String> args) 
		throws IllegalAccessException, InvocationTargetException, InstantiationException
	{
		while (args.hasNext()) {
			if (isSwitch(args.peek())) {
				OptionSpecification optionSpecification = getOptionSpecification(args.peek());
				if (optionSpecification == null) {
					switch(optionSetLevel) {
						case MAIN_OPTIONS:
							throw new UnrecognizedSwitchException(spec.getClass().getName(), args.peek());
						case SUB_GROUP:
							validateAndConsolidate();
							return;
					}
				} else {
					args.next();
					optionSpecification.activateAndConsumeArguments(args);
				}
			} else {
				OptionSpecification looseArgsOptionSpecification = getLooseArgsOptionSpecification();
				if (looseArgsOptionSpecification != null) {
					looseArgsOptionSpecification.activateAndConsumeArguments(args);
				} else {
					switch(optionSetLevel) {
						case MAIN_OPTIONS:
							throw new InvalidCommandLineException("Invalid argument: "+args.peek());
						case SUB_GROUP:
							validateAndConsolidate();
							return;
					}
				}
			}
		}
		flush();
	}
	
	private void flush() 
		throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		for (OptionSpecification option: options) {
			option.flush();
		}
	}
	
	public void validateAndConsolidate() {
	}
	
	private boolean isSwitch(String arg) {
		return arg.matches("\\-.*");
	}
}
