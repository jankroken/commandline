package commandline.domain;

import java.lang.reflect.Method;

public class OptionSpecificationBuilder {
	
	private String longSwitch;
	private String shortSwitch;
	private ArgumentConsumptionBuilder argumentConsumptionBuilder = new ArgumentConsumptionBuilder();
	private Method method;
	
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

	public OptionSpecification getOptionSpecification() {
		Switch _switch = new Switch(longSwitch,shortSwitch);
		ArgumentConsumption argumentConsumption = argumentConsumptionBuilder.getArgumentConsumption();
		OptionSpecification specification = new OptionSpecification(method,_switch,argumentConsumption);
		return specification;
	}

}
