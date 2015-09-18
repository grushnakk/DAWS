package ch.unibe.scg.dicto.states2;

import ch.unibe.scg.dicto.parser.Acceptor;
import static ch.unibe.scg.dicto.parser.Acceptors.whitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;;


public class PathBuilder {
	
	private State destination;
	private Acceptor acceptor;
	private Acceptor prefix;
	private Suggestor suggestor;
	private final StateBuilder parent;

	PathBuilder(StateBuilder parent) {
		this.parent = parent;
		this.prefix = whitespace();
		this.suggestor = Suggestor.NOTHING;
	}
	
	public void setDestination(State destination) {
		this.destination = destination;
	}
	
	public PathBuilder to(State destination) {
		setDestination(destination);
		return this;
	}
	
	public PathBuilder accepts(Acceptor acceptor) {
		this.acceptor = acceptor;
		return this;
	}
	
	public PathBuilder suggestsNothing() {
		this.suggestor = Suggestor.NOTHING;
		return this;
	}
	
	public PathBuilder suggests(String element) {
		this.suggestor = new StaticSuggestor(element);
		return this;
	}
	
	public PathBuilder suggests(Suggestor suggestor) {
		this.suggestor = suggestor;
		return this;
	}
	
	public PathBuilder startsWithWhitespace() {
		this.prefix = whitespace();
		return this;
	}
	
	public PathBuilder startWithOptionalWhitespace() {
		this.prefix = optionalWhitespace();
		return this;
	}
	
	public void complete() {
		parent.addPath(new Path(destination, suggestor, prefix.chain(acceptor)));
	}
}