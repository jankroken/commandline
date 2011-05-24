package commandline.domain;

import java.util.Iterator;

public class TokenIterator implements Iterator<Token> {

	private Iterator<String> stringIterator;
	private boolean argumentEscapeEncountered = false;
	
	public TokenIterator(Iterator<String> stringIterator) {
		this.stringIterator = stringIterator;
	}

	public boolean hasNext() {
		return stringIterator.hasNext();
	}

	public Token next() {
		String value = stringIterator.next();
		if (isArgumentEscape(value)) {
			argumentEscapeEncountered = true;
			return next();
		}
		if (!argumentEscapeEncountered && isSwitch(value)) {
			return new SwitchToken(value);
		} else {
			return new ArgumentToken(value);
		}
	}

	public void remove() {
		stringIterator.remove();
	}
	
	
	private boolean isSwitch(String argument) {
		return argument.matches("-.*");
	}
	
	private boolean isArgumentEscape(String value) {
		return ("--".equals(value) && !argumentEscapeEncountered);
	}
	
	

}
