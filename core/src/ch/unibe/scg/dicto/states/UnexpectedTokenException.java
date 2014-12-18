package ch.unibe.scg.dicto.states;

import ch.unibe.scg.dicto.Token;

public class UnexpectedTokenException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final Token token;

	public UnexpectedTokenException(Token token) {
		super("Unexpected Token: " + token);
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
}
