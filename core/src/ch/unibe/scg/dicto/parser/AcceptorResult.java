package ch.unibe.scg.dicto.parser;

import java.util.HashMap;

public class AcceptorResult {
	
	public static enum State {
		FAILURE, SUCCESS, INCOMPLETE;
	}

	public State state;
	private HashMap<String, String> regions;
	public int begin, end;
	
	public AcceptorResult(int begin, int end, State state) {
		this.state = state;
		this.begin = begin;
		this.end = end;
		this.regions = new HashMap<String, String>();
	}
	

	public AcceptorResult(AcceptorResult result) {
		set(result);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + begin;
		result = prime * result + end;
		result = prime * result + ((regions == null) ? 0 : regions.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		AcceptorResult other = (AcceptorResult) obj;
		if (begin != other.begin)
			return false;
		if (end != other.end)
			return false;
		if (regions == null) {
			if (other.regions != null)
				return false;
		} else if (!regions.equals(other.regions))
			return false;
		if (state != other.state)
			return false;
		return true;
	}


	public boolean isFailure() {
		return state == State.FAILURE;
	}
	
	public boolean isSuccess() {
		return state == State.SUCCESS;
	}
	
	public boolean isIncomplete() {
		return state == State.INCOMPLETE;
	}
	
	public int size() {
		return end - begin;
	}

	
	@Override
	public String toString() {
		return "Result [state=" + state + ", regions=" + regions + ", begin="
				+ begin + ", end=" + end + "]";
	}


	public void set(AcceptorResult result) {
		begin = result.begin;
		end = result.end;
		state = result.state;
		regions = new HashMap<String, String>(result.regions);
	}
	
	public void addRegion(String key, String value) {
		regions.put(key, value);
	}
	
	public String getRegion(String key) {
		return regions.get(key);
	}
}
