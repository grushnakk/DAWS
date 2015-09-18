package ch.unibe.scg.dicto.states2;

import java.util.List;

public class StateMachineError extends StateMachineResult {

	private final String message;
	
	public StateMachineError(String message) {
		this.message = message;
	}
	
	@Override
	public Type getType() {
		return Type.SYNTAX_ERROR;
	}

	@Override
	public List<String> getSuggestions() {
		throw new UnsupportedOperationException("method getSuggestions not supported in StateMachineError");
	}

	@Override
	public String getErrorMessage() {
		return message;
	}

}
