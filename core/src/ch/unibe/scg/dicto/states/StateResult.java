package ch.unibe.scg.dicto.states;

import java.util.List;

public abstract class StateResult {

	public static enum Type {
		SUCCESS, NEXT, ERROR;
	}
	
	public abstract Type getType();
	
	public abstract int getNextStateID();
	
	public abstract List<String> getSuggestions();
	
	public abstract String getErrorMessage();
	
	public boolean isError() {
		return getType() == Type.ERROR;
	}
	
	public boolean isNext() {
		return getType() == Type.NEXT;
	}
	
	public boolean isSuccess() {
		return getType() == Type.SUCCESS;
	}
}
