package commandline.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import commandline.util.PeekIterator;

public class OptionSpecification {
	private Method method;
	private boolean activated = false;
	private Switch _switch;
	private ArgumentConsumption argumentConsumption;
	private boolean required;
	private Occurences occurences;
	private Object spec;
	private ArrayList<Object> argumentBuffer = new ArrayList<Object>();
	
	public OptionSpecification(Object spec, Method method , Switch _switch, ArgumentConsumption argumentConsumption, boolean required2, Occurences occurences) {
		this.spec = spec;
		this._switch = _switch;
		this.method = method;
		this.argumentConsumption = argumentConsumption;
		this.required = required;
		this.occurences = occurences;
	}
	
	public Switch getSwitch() {
		return _switch;
	}

	public void activateAndConsumeArguments(PeekIterator<String> args) 
		throws InvocationTargetException, IllegalAccessException, InstantiationException
	{
		System.out.println("method="+method+" spec="+spec);
		
		activated = true;
		switch(argumentConsumption.getType()) {
			case NO_ARGS:
				handleArguments(argumentConsumption.getToggleValue());
//				method.invoke(spec, argumentConsumption.getToggleValue());
				break;
			case SINGLE_ARGUMENT:
				// TODO : check existence of argument
				String argument = args.next();
				handleArguments(argument);
//				method.invoke(spec, argument);
				break;
			case ALL_AVAILABLE:
				ArrayList<String> allArguments = new ArrayList<String>();
				while(args.hasNext() && !isSwitch(args.peek())) {
					allArguments.add(args.next());
				}
				handleArguments(allArguments);
//				method.invoke(spec,allArguments);
				break;
			case UNTIL_DELIMITER:
				ArrayList<String> delimitedArguments = new ArrayList<String>();
				while(args.hasNext() && !args.peek().equals(argumentConsumption.getDelimiter())) {
					delimitedArguments.add(args.next());
				}
				if (args.hasNext()) args.next();
				handleArguments(delimitedArguments);
//				method.invoke(spec,delimitedArguments);
				break;
			case SUB_SET:
				Object subset = argumentConsumption.getSubsetClass().newInstance();
				OptionSet subsetOptions = new OptionSet(subset,OptionSetLevel.SUB_GROUP);
				subsetOptions.consumeOptions(args);
				System.out.println("Handling subset: "+subset);
				handleArguments(subset);
//				method.invoke(spec, subset);
				break;
			default:
				throw new RuntimeException("Not implemented: "+argumentConsumption.getType());
		}
	}
	
	private boolean isSwitch(String arg) {
		return arg.matches("-.*");
	}
	
	private void handleArguments(Object args) 
		throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		if (occurences == Occurences.SINGLE) {
			method.invoke(spec,args);
		} else {
			argumentBuffer.add(args);
		}
	}

	public void flush() 
		throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		if (required && !activated) {
			throw new  InvalidCommandLineException("Required argument not specified");
		}
		if (occurences == Occurences.MULTIPLE) {
			method.invoke(spec, argumentBuffer);
		}
	}

}