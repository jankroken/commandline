package commandline.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import commandline.util.PeekIterator;

public class OptionSpecification {
	Method method;
	private boolean activated = false;
	private Switch _switch;
	private ArgumentConsumption argumentConsumption; 
	
	public OptionSpecification(Method method , Switch _switch, ArgumentConsumption argumentConsumption) {
		this._switch = _switch;
		this.method = method;
		this.argumentConsumption = argumentConsumption;
	}
	
	public Switch getSwitch() {
		return _switch;
	}

	public void activateAndConsumeArguments(Object spec, PeekIterator<String> args) 
		throws InvocationTargetException, IllegalAccessException
	{
		System.out.println("method="+method+" spec="+spec);
		
		activated = true;
		switch(argumentConsumption.getType()) {
		case NO_ARGS:
			method.invoke(spec, argumentConsumption.getToggleValue());
			break;
		case SINGLE_ARGUMENT:
			// TODO : check existence of argument
			String argument = args.next();		
			method.invoke(spec, argument);
			break;
		}
	}
}
