package ch.unibe.scg.dicto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {

	private final boolean start, end;
	
	private final Map<TokenType, State> successors;
	
	public State(boolean start, boolean end, Map<TokenType, State> successors) {
		this.start = start;
		this.end = end;
		this.successors = new HashMap<>(successors);
	}
	
	public State(Map<TokenType, State> successors) {
		this(false, false, successors);
	}
	
	public boolean isStartState() {
		return start;
	}
	
	public boolean isEndState() {
		return end;
	}
	
	public State consume(int currentIndex, List<Token> tokens) throws UnexpectedTokenException {
		Token currentToken = tokens.get(currentIndex);
		if(!successors.containsKey(currentToken.getTokenType())) {
			throw new UnexpectedTokenException();
		}
		return successors.get(currentToken.getTokenType()).consume(currentIndex + 1, tokens);
	}

	@Override
	public String toString() {
		return "State [start=" + start + ", end=" + end + ", successors="
				+ successors + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (end ? 1231 : 1237);
		result = prime * result + (start ? 1231 : 1237);
		result = prime * result
				+ ((successors == null) ? 0 : successors.hashCode());
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
		State other = (State) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		if (successors == null) {
			if (other.successors != null)
				return false;
		} else if (!successors.equals(other.successors))
			return false;
		return true;
	}
	
	public static class StateBuilder {
		private boolean end, start;
		private Map<TokenType, State> successors;
		
		public static StateBuilder start() {
			return new StateBuilder().setStartState();
		}
		
		public static StateBuilder state() {
			return new StateBuilder();
		}
		
		public StateBuilder() {
			this.end = false;
			this.start = false;
			this.successors = new HashMap<>();
		}
		
		public StateBuilder setStartState() {
			start = true;
			return this;
		}
		
		public StateBuilder setEndState() {
			this.end = true;
			return this;
		}
		
		public State build() {
			return new State(start, end, successors);
		}
		
		public StateBuilder successor(TokenType type, StateBuilder builder) {
			this.successors.put(type, builder.build());
			return this;
		}
	}
}
