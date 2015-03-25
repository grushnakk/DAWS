package ch.unibe.scg.dicto.states;

import java.util.List;

import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.Context;

public interface Path {
	
	public AcceptorResult accept(Context context);
	
	public StateResult onSuccess(Environment env, AcceptorResult result);
	
	public List<String> suggestions(Environment env);
}
