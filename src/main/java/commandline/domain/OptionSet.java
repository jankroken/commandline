package commandline.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import commandline.util.PeekIterator;

public class OptionSet {
	private List<OptionSpecification> options;
	private OptionSetLevel optionSetLevel;
	private Object spec;
	
	public OptionSet(Object spec, OptionSetLevel optionSetLevel) {
		this.spec = spec;
		System.out.println("spec: "+spec);
		options = OptionSpecificationFactory.getOptionSpecifications(spec.getClass());
		System.out.println("Option count: "+options.size());
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
	
	public void consumeOptions(PeekIterator<String> args) 
		throws IllegalAccessException, InvocationTargetException
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
					optionSpecification.activateAndConsumeArguments(spec,args);
				}
			} else {
				if (handlesLooseArguments()) {
					handleLooseArgument(args.next());
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
			
	}
	
	private void handleLooseArgument(String argument) {
		
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
