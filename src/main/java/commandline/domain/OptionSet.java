package commandline.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import commandline.util.PeekIterator;

public class OptionSet {
	private List<OptionSpecification> options;
	private OptionSetLevel optionSetLevel;
	
	public OptionSet(Object spec, OptionSetLevel optionSetLevel) {
		options = OptionSpecificationFactory.getOptionSpecifications(spec,spec.getClass());
		this.optionSetLevel = optionSetLevel;
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
							throw new InvalidCommandLineException("Unrecognized switch: "+args.peek());
						case SUB_GROUP:
							validateAndConsolidate();
							return;
					}
				} else {
					args.next();
					optionSpecification.activateAndConsumeArguments(args);
				}
			} else {
				if (handlesLooseArguments()) {
					handleLooseArguments(args);
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
	
	private void handleLooseArguments(PeekIterator<String> args) 
		throws InvocationTargetException, IllegalAccessException, InstantiationException
	{
		getLooseArgsOptionSpecification().activateAndConsumeArguments(args);
	}
	
	private boolean handlesLooseArguments() {
		return true;
	}
	
	public void validateAndConsolidate() {
		
	}
	
	private boolean isSwitch(String arg) {
		return arg.matches("\\-.*");
	}
}
