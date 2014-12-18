package ch.unibe.scg.dicto.states;

import ch.unibe.scg.dicto.Token;
import ch.unibe.scg.dicto.TokenType;

/**
 * accepts only tokens of a specific type. 
 * @author Kenneth
 *
 */
public abstract class TokenTypeState extends AbstractState {
	
	protected final TokenType tokenType;
	
	public TokenTypeState(TokenType tokenType) {
		this.tokenType = tokenType;
	}
	
	@Override
	protected boolean matches(Token token) {
		return this.tokenType.equals(token.getType());
	}
}
