package commandline.domain;

import java.util.LinkedList;

import commandline.util.PeekIterator;

public class TokenQueue {
	
	private PeekIterator<String> iterator;
	private LinkedList<Token> tokens = new LinkedList<Token>();
	private PeekIterator<String> stringIterator;
	private boolean argumentEscapeEncountered = false; 
	private String argumentTerminator = null;
	private LinkedList<SwitchToken> splitTokens = new LinkedList<SwitchToken>();
	
	
	public boolean hasMore() {
		return !tokens.isEmpty() && !iterator.hasNext();
	}
	
	
}
