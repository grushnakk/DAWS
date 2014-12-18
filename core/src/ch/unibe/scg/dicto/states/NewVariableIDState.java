package ch.unibe.scg.dicto.states;

import java.util.HashMap;

import ch.unibe.scg.dicto.Token;
import ch.unibe.scg.dicto.TokenType;

/**
 * when a new variable is about to be created
 * @author Kenneth
 *
 */
public class NewVariableIDState extends TokenTypeState {

	public NewVariableIDState(State next) {
		super(new TokenType("IDENTIFIER"), next); //FIXME magic value
	}
	
	@Override
	protected State success(Token token, StateMachine stateMachine) {
		stateMachine.put("VAR_ARGS", new HashMap<String, String>()); //FIXME magic value
		stateMachine.put("VAR_NAME", token.getContent()); //FIXME magic value
		return next;
	}

	@Override
	protected void failure(Token token, StateMachine stateMachine) {
		// TODO Auto-generated method stub
		
	}
}
