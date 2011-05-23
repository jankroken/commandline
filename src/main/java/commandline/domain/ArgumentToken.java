package commandline.domain;

public class ArgumentToken implements Token {
	String value;
	
	public ArgumentToken(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
