package ch.unibe.scg.dicto.states;

import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.Context;

public abstract class Path {
	
	private final Acceptor acceptor;
	
	public Path(Acceptor acceptor) {
		this.acceptor = acceptor;
	} 
	
	public AcceptorResult accept(Context context) { return acceptor.accept(context); }
	

	public abstract StateResult onNext(Environment env, AcceptorResult result);
	
	public abstract List<String> suggestions(Environment env);
}
