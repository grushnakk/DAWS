package ch.unibe.scg.dicto.states2;

import java.util.List;

public interface StateMachineResult {

	public static enum Type {SYNTAX_ERROR, SUCCESS;}
	
	public Type getType();
	
	public List<String> getSuggestions();
}
