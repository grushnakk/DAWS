package ch.unibe.scg.dicto.states;

import java.util.List;

public class Error extends StateResult {
	
	private final String errorMessage;
	
	public Error(String message) {
		this.errorMessage = message;
	}

	@Override
	public Type getType() {
		return Type.ERROR;
	}

	@Override
	public State getNextState() {
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

}
