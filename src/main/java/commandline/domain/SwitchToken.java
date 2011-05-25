package commandline.domain;

public class SwitchToken implements Token {
	String value;
	String argumentValue;
	
	public SwitchToken(String value, String argumentValue) {
		this.value = value;
		this.argumentValue = argumentValue;
	}
	public String getValue() {
		return value;
	}
	
	public String getArgumentValue() {
		return argumentValue;
	}
	
	public String toString() {
		return "<SwitchToken:"+value+"("+argumentValue+")>";
	}
}
