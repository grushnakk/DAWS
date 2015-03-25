package ch.unibe.scg.dicto.states;

import java.util.List;

public class Next extends StateResult {

	private final State nextState;
	
	public Next(State nextState) {
		this.nextState = nextState;
	}
	
	@Override
	public Type getType() {
		return Type.NEXT;
	}

	@Override
	public State getNextState() {
		return nextState;
	}

	@Override
	public List<String> getSuggestions() {
		throw new UnsupportedOperationException("getSuggestions is not supported by Type Next");
	}

	@Override
	public String getErrorMessage() {
		throw new UnsupportedOperationException("getErrorMessage is not supported by Type Next");
	}

}
