package ch.unibe.scg.dicto.states2;

import ch.unibe.scg.dicto.parser.Acceptor;
import static ch.unibe.scg.dicto.parser.Acceptors.whitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;;


public class PathBuilder {
	
	private State destination;
	private Acceptor acceptor;
	private Acceptor whitespacePrefix;
	private Suggestor suggestor;
	private AfterSuccessAction afterSuccessAction;
	private final StateBuilder parent;

	PathBuilder(StateBuilder parent) {
		this.parent = parent;
		this.whitespacePrefix = whitespace();
		this.suggestor = Suggestor.NOTHING;
		this.afterSuccessAction = AfterSuccessAction.NO_ACTION;
	}
	
	public void setDestination(State destination) {
		this.destination = destination;
	}
	
	public PathBuilder to(State destination) {
		setDestination(destination);
		return this;
	}
	
	public PathBuilder accept(Acceptor acceptor) {
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
	
	public PathBuilder suggest(Suggestor suggestor) {
		this.suggestor = suggestor;
		return this;
	}
	
	public PathBuilder startsWithWhitespace() {
		this.whitespacePrefix = whitespace();
		return this;
	}
	
	public PathBuilder startWithOptionalWhitespace() {
		this.whitespacePrefix = optionalWhitespace();
		return this;
	}
	
	public PathBuilder onSuccess(AfterSuccessAction action) {
		this.afterSuccessAction = action;
		return this;
	}
	
	public void complete() {
		parent.addPath(new Path(destination, suggestor, whitespacePrefix.chain(acceptor), afterSuccessAction));
	}
}