package ch.unibe.scg.dicto.states;

import ch.unibe.scg.dicto.Token;

abstract class AbstractState implements State {
	
	protected final State next;
	
	AbstractState(State next) {
		this.next = next;
	}
	
	@Override
	public State consume(Token token, StateMachine stateMachine) {
		if(matches(token, stateMachine)) {
			return success(token, stateMachine);
		}
		failure(token, stateMachine);
		return null;
	}
	
	protected abstract boolean matches(Token token, StateMachine stateMachine);
	
	protected abstract State success(Token token, StateMachine stateMachine);
	
	protected abstract void failure(Token token, StateMachine stateMachine); //TODO do I need this?
}
