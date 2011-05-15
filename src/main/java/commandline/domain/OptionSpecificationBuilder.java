package commandline.domain;

import java.lang.reflect.Method;

public class OptionSpecificationBuilder {
	
	private String longSwitch;
	private String shortSwitch;
	private ArgumentConsumptionBuilder argumentConsumptionBuilder = new ArgumentConsumptionBuilder();
	private Method method;
	private Occurences occurences = Occurences.SINGLE;
	private boolean required = false;
	private Object spec;
	
	public void addMethod(Method method) {
		this.method = method;
	}
	
	public void addLongSwitch(String longSwitch) {
		this.longSwitch = longSwitch;
	}
	
	public void addShortSwitch(String shortSwitch) {
		this.shortSwitch = shortSwitch;
	}
	
	public void addToggle(boolean value) {
		argumentConsumptionBuilder.addNoArgs(value);
	}

	public void addSingleArgument() {
		argumentConsumptionBuilder.addSingleArgument();
	}
	
	public void addAllAvailableArguments() {
		argumentConsumptionBuilder.addAllAvailable();
	}
	
	public void addUntilDelimiter(String delimiter) {
		argumentConsumptionBuilder.addUntilDelimiter(delimiter);
	}
	
	public void addSubset(Class<? extends Object> clazz) {
		argumentConsumptionBuilder.addSubSet(clazz);
	}
	
	public void addRequired() {
		required = true;
	}
	
	public void addOccurences(Occurences occurences) {
		this.occurences = occurences;
	}

	public void addConfiguration(Object spec) {
		this.spec = spec;
	}
	
	public OptionSpecification getOptionSpecification() {
		Switch _switch = new Switch(longSwitch,shortSwitch);
		ArgumentConsumption argumentConsumption = argumentConsumptionBuilder.getArgumentConsumption();
		OptionSpecification specification = new OptionSpecification(spec,method,_switch,argumentConsumption, required, occurences);
		return specification;
	}


}
