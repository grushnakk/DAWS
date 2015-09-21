package ch.unibe.scg.dicto.states2;

import ch.unibe.scg.dicto.parser.Acceptor;

public class Path {
	
	private State destination;
	private final AfterSuccessAction afterSuccessAction;
	private final Suggestor suggestor;
	private final Acceptor acceptor;
	
	public Path(State destination, Suggestor suggestor, Acceptor acceptor, AfterSuccessAction asa) {
		this.destination = destination;
		this.suggestor = suggestor;
		this.acceptor = acceptor;
		this.afterSuccessAction = asa;
	}

	public State getDestination() {
		return destination;
	}
	
	void setDestination(State destination) {
		this.destination = destination;
	}
	
	public Suggestor getSuggestor() {
		return suggestor;
	}
	
	public Acceptor getAcceptor() {
		return acceptor;
	}
	
	public AfterSuccessAction getAfterSuccessAction() {
		return afterSuccessAction;
	}
}
