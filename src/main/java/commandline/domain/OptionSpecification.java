package commandline.domain;

import java.lang.reflect.Method;

import commandline.util.PeekIterator;

public class OptionSpecification {
	Method method;
	private boolean activated = false;
	private Switch _switch;
	private ArgumentConsumption argumentConsumption; 
	
	public OptionSpecification(Method method , Switch _switch, ArgumentConsumption argumentConsumption) {
		this._switch = _switch;
		this.argumentConsumption = argumentConsumption;
	}
	
	public Switch getSwitch() {
		return _switch;
	}

	public void activateAndConsumeArguments(PeekIterator<String> args) {
		activated = true;
		switch(argumentConsumption.getType()) {
		case NO_ARGS:
	
		}
	}
}
