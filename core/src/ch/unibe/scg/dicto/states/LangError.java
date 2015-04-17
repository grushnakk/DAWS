package ch.unibe.scg.dicto.states;

import java.util.List;

public class LangError extends StateResult {
	
	private final String errorMessage;
	
	public LangError(String message) {
		this.errorMessage = message;
	}

	@Override
	public Type getType() {
		return Type.ERROR;
	}

	@Override
	public int getNextStateID() {
		throw new UnsupportedOperationException("getNextState is not supported by Type Error");
	}

	@Override
	public List<String> getSuggestions() {
		throw new UnsupportedOperationException("getSuggestions is not supported by Type Error");
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		return "LangError [errorMessage=" + errorMessage + "]";
	}
}
