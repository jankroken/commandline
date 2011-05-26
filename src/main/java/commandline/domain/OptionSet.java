package commandline.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import commandline.util.PeekIterator;


public class OptionSet {
	private List<OptionSpecification> options;
	private OptionSetLevel optionSetLevel;
	private Object spec;
	
	public OptionSet(Object spec, OptionSetLevel optionSetLevel) {
		options = OptionSpecificationFactory.getOptionSpecifications(spec,spec.getClass());
		this.optionSetLevel = optionSetLevel;
		this.spec = spec;
	}
	
	public OptionSpecification getOptionSpecification(SwitchToken _switch) {
		for (OptionSpecification optionSpecification : options) {
			if (optionSpecification.getSwitch().matches(_switch.getValue())) {
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
	
	public void consumeOptions(Tokenizer args) 
		throws IllegalAccessException, InvocationTargetException, InstantiationException
	{
		while (args.hasNext()) {
			if (args.peek() instanceof SwitchToken) {
				OptionSpecification optionSpecification = getOptionSpecification((SwitchToken)args.peek());
				if (optionSpecification == null) {
					switch(optionSetLevel) {
						case MAIN_OPTIONS:
							throw new UnrecognizedSwitchException(spec.getClass(), args.peek().getValue());
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
}
