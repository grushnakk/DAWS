package ch.unibe.scg.dicto.states;

import java.util.List;

import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.Context;

public class Path implements NextAction, SuggestAction {
	
	private final Acceptor acceptor;
	private final NextAction nextAction;
	private final SuggestAction suggestAction;
	
	public Path(Acceptor acceptor, NextAction nextAction, SuggestAction suggestAction) {
		this.acceptor = acceptor;
		this.suggestAction = suggestAction;
		this.nextAction = nextAction;
	} 
	
	public AcceptorResult accept(Context context) { return acceptor.accept(context); }
	
	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		return nextAction.onNext(env, result);
	}
	
	@Override
	public List<String> suggestions(Environment env) {
		return suggestAction.suggestions(env);
	}
}
