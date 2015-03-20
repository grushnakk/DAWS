package ch.unibe.scg.dicto.parser;

public class Result {
	
	public static enum State {
		FAILURE, SUCCESS, INCOMPLETE;
	}

	public State state;
	public int begin, end;
	
	public Result(int begin, int end, State state) {
		this.state = state;
		this.begin = begin;
		this.end = end;
	}
	

	public Result(Result result) {
		set(result);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + begin;
		result = prime * result + end;
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
		Result other = (Result) obj;
		if (begin != other.begin)
			return false;
		if (end != other.end)
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
		return "Result [state=" + state + ", begin=" + begin + ", end=" + end
				+ "]";
	}
	
	public void set(Result result) {
		begin = result.begin;
		end = result.end;
		state = result.state;
	}
}
