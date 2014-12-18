package ch.unibe.scg.dicto.states;

import ch.unibe.scg.dicto.Token;
import ch.unibe.scg.dicto.TokenType;

public class KeywordState extends TokenTypeState {
	
	protected final State next;

	public KeywordState(TokenType tokenType, State next) {
		super(tokenType);
		this.next = next;
	}

	@Override
	protected State success(Token token) {
		return next;
	}

	@Override
	protected void failure(Token token) {
	}

}
