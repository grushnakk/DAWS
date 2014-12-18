package ch.unibe.scg.dicto.states;

import ch.unibe.scg.dicto.Token;
import ch.unibe.scg.dicto.TokenType;

/**
 * when a new variable is about to be created
 * @author Kenneth
 *
 */
public class NewVariableIDState extends TokenTypeState {

	public NewVariableIDState() {
		super(new TokenType("IDENTIFIER"));
	}
	
	@Override
	protected State success(Token token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void failure(Token token) {
		// TODO Auto-generated method stub
		
	}
}
