package commandline.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import commandline.annotations.SubConfiguration;
import commandline.error.InvalidConfigurationTests;
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
	
	public void validate() {
		if (_switch == null || (_switch.getShortSwitch() == null && _switch.getLongSwitch() == null)) {
			throw new InvalidOptionSpecificationException("Option specified without switchess");
		}
		validateType();
	}
	
	private void validateType() {
		Type[] types = method.getGenericParameterTypes(); 
		if (types == null || types.length != 1) {
			throw new InvalidOptionSpecificationException("Wrong number of arguments, expecting exactly one");
		}
		Type type = types[0];
//		System.out.println("method="+method.getName()+" type: "+type+" type-as-class: "+(Class)type);
		int listLevel = getListLevel();
		verifyType(listLevel,getInnerArgumentType(),type,type);
	}
	
	private void verifyType(int listLevel, Class<? extends Object> innerClass, Type type, Type fullType) {
		if (listLevel > 0) {
			System.out.println("List compare ["+clazz(type)+"] list=["+List.class+"]");
			System.out.println("List compare clazz(:1) isAssigneableFrom(:2) => "+((clazz(type).isAssignableFrom(List.class))));
			if (!((clazz(type)).isAssignableFrom(List.class))) {
				wrongType(fullType);
			} else {
				Type innerType = ((ParameterizedType)type).getActualTypeArguments()[0];
				verifyType(listLevel-1,innerClass,innerType, fullType);
			}
		} else {
			System.out.println("comparing innerType "+getInnerArgumentType()+" with actual class "+(Class)innerClass);
			System.out.println("innerArgumentType: "+getInnerArgumentType()+" class="+getInnerArgumentType().getClass());
			System.out.println("innerClass: "+innerClass+" class="+innerClass.getClass());
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
			throw new RuntimeException("Don't know how to get the class from type "+type);
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
		throw new InvalidOptionSpecificationException("Wrong argument type, expected "+getExpectedTypeDescription()+" but found "+type);
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
			default:
				throw new RuntimeException("Internal error");
		}
	}

	public Switch getSwitch() {
		return _switch;
	}

	public void activateAndConsumeArguments(PeekIterator<String> args) 
		throws InvocationTargetException, IllegalAccessException, InstantiationException
	{
		activated = true;
		switch(argumentConsumption.getType()) {
			case NO_ARGS:
				handleArguments(argumentConsumption.getToggleValue());
				break;
			case SINGLE_ARGUMENT:
				// TODO : check existence of argument
				String argument = args.next();
				handleArguments(argument);
				break;
			case ALL_AVAILABLE:
				ArrayList<String> allArguments = new ArrayList<String>();
				while(args.hasNext() && !isSwitch(args.peek())) {
					allArguments.add(args.next());
				}
				handleArguments(allArguments);
				break;
			case UNTIL_DELIMITER:
				ArrayList<String> delimitedArguments = new ArrayList<String>();
				while(args.hasNext() && !args.peek().equals(argumentConsumption.getDelimiter())) {
					delimitedArguments.add(args.next());
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