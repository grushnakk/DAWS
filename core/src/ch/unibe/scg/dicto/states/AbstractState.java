package ch.unibe.scg.dicto.states;

import ch.unibe.scg.dicto.Token;

abstract class AbstractState implements State {
	
	@Override
	public State consume(Token token, StateMachine stateMachine) {
		if(matches(token)) {
			return success(token);
		}
		failure(token);
		return null;
	}
	
	protected abstract boolean matches(Token token);
	
	protected abstract State success(Token token);
	
	protected abstract void failure(Token token); //TODO is this needed?
}
