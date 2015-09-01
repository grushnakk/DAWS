package ch.unibe.scg.dicto.states2;

import ch.unibe.scg.dicto.parser.Acceptor;

public class Path {
	
	private final State destination;
	private final Suggestor suggestor;
	private final Acceptor acceptor;
	
	public Path(State destination, Suggestor suggestor, Acceptor acceptor) {
		this.destination = destination;
		this.suggestor = suggestor;
		this.acceptor = acceptor;
	}

	public State getDestination() {
		return destination;
	}
}
