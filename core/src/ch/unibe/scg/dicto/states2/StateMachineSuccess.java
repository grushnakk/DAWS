package ch.unibe.scg.dicto.states2;

import java.util.Collections;
import java.util.List;

public class StateMachineSuccess implements StateMachineResult {

	private final List<String> suggestions;
	
	public StateMachineSuccess(List<String> suggestions) {
		this.suggestions = Collections.unmodifiableList(suggestions);
	}
	
	@Override
	public Type getType() {
		return Type.SUCCESS;
	}

	@Override
	public List<String> getSuggestions() {
		return suggestions;
	}

	@Override
	public String getErrorMessage() {
		throw new UnsupportedOperationException("getErrorMessage is not supported in StateMachineSuccess");
	}

}
