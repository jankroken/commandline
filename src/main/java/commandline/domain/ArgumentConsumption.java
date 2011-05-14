package commandline.domain;

public class ArgumentConsumption {
	ArgumentConsumptionType type;
	String delimiter;
	boolean toggleValue;
	Class<? extends Object> subsetClass;
	
	public ArgumentConsumption(ArgumentConsumptionType type) {
		this.type = type;
	}
	
	public ArgumentConsumption(ArgumentConsumptionType type, String delimiter) {
		this.type = type;
		this.delimiter = delimiter;
	}
	
	public ArgumentConsumption(ArgumentConsumptionType type, boolean toggleValue) {
		this.type = type;
		this.toggleValue = toggleValue;
	}

	public ArgumentConsumption(ArgumentConsumptionType type, Class<? extends Object> subsetClass) {
		this.type = type;
		this.subsetClass = subsetClass;
	}
	
	public ArgumentConsumptionType getType() {
		return type;
	}
	
	public String getDelimiter() {
		return delimiter;
	}
	
	public Class<? extends Object> getSubsetClass() {
		return subsetClass;
	}

	public boolean getToggleValue() {
		return toggleValue;
	}
}
