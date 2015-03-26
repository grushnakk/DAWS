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

	@Override
	public String toString() {
		return "Next [nextStateID=" + nextStateID + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nextStateID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Next other = (Next) obj;
		if (nextStateID != other.nextStateID)
			return false;
		return true;
	}
}
