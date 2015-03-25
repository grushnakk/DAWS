package ch.unibe.scg.dicto.states;

import java.util.List;

public class Next extends StateResult {

	private final int nextStateID;
	
	public Next(int nextStateID) {
		this.nextStateID = nextStateID;
	}
	
	@Override
	public Type getType() {
		return Type.NEXT;
	}

	@Override
	public int getNextStateID() {
		return nextStateID;
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
