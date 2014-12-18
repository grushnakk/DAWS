package ch.unibe.scg.dicto.states;

import ch.unibe.scg.dicto.Token;
import ch.unibe.scg.dicto.TokenType;

public class KeywordState extends TokenTypeState {

	public KeywordState(TokenType tokenType, State next) {
		super(tokenType, next);
	}

	@Override
	protected State success(Token token, StateMachine stateMachine) {
		return next;
	}

	@Override
	protected void failure(Token token, StateMachine stateMachine) {
	}

}
