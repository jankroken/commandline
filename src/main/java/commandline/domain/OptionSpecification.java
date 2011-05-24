package commandline.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
	
	public OptionSpecification(Object spec, Method method , Switch _switch, ArgumentConsumption argumentConsumption, boolean required, Occurences occurences) {
		this.spec = spec;
		this._switch = _switch;
		this.method = method;
		this.argumentConsumption = argumentConsumption;
		this.required = required;
		this.occurences = occurences;
 		validate();
	}
	
	public boolean isLooseArgumentsSpecification() {
		return argumentConsumption.getType() == ArgumentConsumptionType.LOOSE_ARGS;
	}
	
	public void validate() {
		if (argumentConsumption.getType() != ArgumentConsumptionType.LOOSE_ARGS) { 
			if (_switch == null || (_switch.getShortSwitch() == null && _switch.getLongSwitch() == null)) {
				throw createInvalidOptionSpecificationException("Option specified without switchess");
			}
		}
		validateType();
	}
	
	private void validateType() {
		Type[] types = method.getGenericParameterTypes(); 
		if (types == null || types.length != 1) {
			throw createInvalidOptionSpecificationException("Wrong number of arguments, expecting exactly one");
 		}
		Type type = types[0];
		int listLevel = getListLevel();
		verifyType(listLevel,getInnerArgumentType(),type,type);
	}
	
	private void verifyType(int listLevel, Class<? extends Object> innerClass, Type type, Type fullType) {
		if (listLevel > 0) {
			if (!((clazz(type)).isAssignableFrom(List.class))) {
				wrongType(fullType);
			} else {
				Type innerType = ((ParameterizedType)type).getActualTypeArguments()[0];
				verifyType(listLevel-1,innerClass,innerType, fullType);
			}
		} else {
			if (!box(clazz(type)).isAssignableFrom(innerClass)) {
				wrongType(fullType);
			}
		}
	}
	
	private Class clazz(Type type) {
		if (type instanceof ParameterizedType) {
			return (Class) ((ParameterizedType) type).getRawType();
		} else if (type instanceof Class) {
			return (Class) type;
		} else {
			throw createInternalErrorException("Don't know how to get the class from type "+type);
		}
	}
	
	private Class<? extends Object> box(Class<? extends Object> clazz) {
		if (clazz == boolean.class) {
			return Boolean.class;
		} else {
			return clazz;
		}
	}
	
	private void wrongType(Type type) {
		throw createInvalidOptionSpecificationException("Wrong argument type, expected "+getExpectedTypeDescription()+" but found "+type);
	}
	
	private String getExpectedTypeDescription() {
		StringBuilder sb = new StringBuilder();
		int listLevel = getListLevel();
		for (int i = 0; i < listLevel;i++) {
			sb.append("List<");
		}
		sb.append(getInnerArgumentType());
		for (int i = 0; i < listLevel;i++) {
			sb.append(">");
		}
		return sb.toString();
	}
	
	private int getListLevel() {
		int listLevel = 0;
		if (occurences == Occurences.MULTIPLE) {
			listLevel++;
		}
		switch(argumentConsumption.getType()) {
			case NO_ARGS:
			case SINGLE_ARGUMENT:
			case SUB_SET:
				break;
			case ALL_AVAILABLE:
			case UNTIL_DELIMITER:
				listLevel++;
				break;
		}
		return listLevel;
	}
	
	private Class<? extends Object> getInnerArgumentType() {
		switch(argumentConsumption.getType()) {
			case NO_ARGS:
				return Boolean.class;
			case SINGLE_ARGUMENT:
				return String.class;
			case SUB_SET:
				return argumentConsumption.getSubsetClass();
			case ALL_AVAILABLE:
				return String.class;
			case UNTIL_DELIMITER:
				return String.class;
			case LOOSE_ARGS:
				return String.class;
			default:
				throw new RuntimeException("Internal error");
		}
	}

	public Switch getSwitch() {
		return _switch;
	}

	public void activateAndConsumeArguments(Tokenizer args) 
		throws InvocationTargetException, IllegalAccessException, InstantiationException
	{
		activated = true;
		switch(argumentConsumption.getType()) {
			case NO_ARGS:
				handleArguments(argumentConsumption.getToggleValue());
				break;
			case SINGLE_ARGUMENT:
				if (!args.hasNext() || args.peek() instanceof SwitchToken) {
					throw createInvalidCommandLineException("Missing argument");
				}
				String argument = args.next().getValue();
				handleArguments(argument);
				break;
			case ALL_AVAILABLE:
				ArrayList<String> allArguments = new ArrayList<String>();
				while(args.hasNext() && !(args.peek() instanceof SwitchToken)) {
					allArguments.add(args.next().getValue());
				}
				handleArguments(allArguments);
				break;
			case UNTIL_DELIMITER:
				args.setArgumentTerminator(argumentConsumption.getDelimiter());
				ArrayList<String> delimitedArguments = new ArrayList<String>();
				while(args.hasNext() && !args.peek().getValue().equals(argumentConsumption.getDelimiter())) {
					delimitedArguments.add(args.next().getArgumentValue());
				}
				if (args.hasNext()) args.next();
				handleArguments(delimitedArguments);
				break;
			case SUB_SET:
				Object subset = argumentConsumption.getSubsetClass().newInstance();
				OptionSet subsetOptions = new OptionSet(subset,OptionSetLevel.SUB_GROUP);
				subsetOptions.consumeOptions(args);
				handleArguments(subset);
				break;
			case LOOSE_ARGS:
				handleArguments(args.next().getValue());
				break;
			default:
				throw createInternalErrorException("Not implemented: "+argumentConsumption.getType());
		}
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
			throw createInvalidCommandLineException("Required argument not specified");
		}
		if (occurences == Occurences.MULTIPLE) {
			method.invoke(spec, argumentBuffer);
		}
	}
	
	private InvalidOptionConfigurationException createInvalidOptionSpecificationException(String description) {
		return new InvalidOptionConfigurationException(getOptionId()+' '+description);
	}

	private InvalidCommandLineException createInvalidCommandLineException(String description) {
		return new InvalidCommandLineException(getOptionId()+' '+description);
	}
	
	private RuntimeException createInternalErrorException(String description) {
		return new InternalErrorException(getOptionId()+' '+description);
	}
	
	public String getOptionId() {
		StringBuilder sb = new StringBuilder();
		sb.append("[")
		  .append(spec.getClass().getName())
		  .append(":")
		  .append(method.getName())
		  .append("]");
		return sb.toString();
	}
	
}