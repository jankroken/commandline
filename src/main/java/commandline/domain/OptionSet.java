package commandline.domain;

import java.util.List;

import commandline.util.PeekIterator;

public class OptionSet {
	private List<OptionSpecification> options;
	private OptionSetLevel optionSetLevel;
	private Class<? extends Object> clazz;
	
	public OptionSet(Class<? extends Object> clazz, OptionSetLevel optionSetLevel) {
		this.clazz = clazz;
		options = OptionSpecificationFactory.getOptionSpecifications(clazz);
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
	
	public void consumeOptions(PeekIterator<String> args) {
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
