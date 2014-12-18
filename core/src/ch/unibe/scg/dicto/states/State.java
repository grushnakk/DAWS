package ch.unibe.scg.dicto.states;

import ch.unibe.scg.dicto.Token;

public interface State {
	
	/**
	 * tries to consume the next token.
	 * @return the new active state, if the token was valid - null otherwise
	 */
	public State consume(Token token, StateMachine stateMachine);
}
