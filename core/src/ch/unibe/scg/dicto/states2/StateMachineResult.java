package ch.unibe.scg.dicto.states2;

import java.util.List;

public abstract class StateMachineResult {

	public static enum Type {SYNTAX_ERROR, SUCCESS;}
	
	public abstract Type getType();
	
	public boolean isSuccess() {
		return getType() == Type.SUCCESS;
	}
	
	public boolean isSyntaxError() {
		return getType() == Type.SYNTAX_ERROR;
	}
	
	public abstract List<String> getSuggestions();
	
	public abstract String getErrorMessage();
}
