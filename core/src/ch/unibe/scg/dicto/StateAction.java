package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.parser.Acceptor;

public abstract class StateAction {

	private final Acceptor acceptor;
	
	public StateAction(Acceptor acceptor) {
		this.acceptor  = acceptor;
	}
	
	public abstract void action(StateMachine stateMachine);
	
	public Acceptor getAcceptor() {
		return acceptor;
	}
}
