package commandline.domain;

public class SwitchToken implements Token {
	String value;
	
	public SwitchToken(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
