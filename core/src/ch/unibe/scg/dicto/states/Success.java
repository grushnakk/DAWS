package ch.unibe.scg.dicto.states;

import java.util.Collections;
import java.util.List;

public class Success extends StateResult {
	
	private final List<String> suggestions;
	
	public Success(List<String> suggestions) {
		this.suggestions = Collections.unmodifiableList(suggestions);
	}

	@Override
	public Type getType() {
		return Type.SUCCESS;
	}

	@Override
	public State getNextState() {
		throw new UnsupportedOperationException("getNextSTate is not supported by Type Error");
	}

	@Override
	public List<String> getSuggestions() {
		return suggestions;
	}

	@Override
	public String getErrorMessage() {
		throw new UnsupportedOperationException("getErrorMessage is not supported by Type Error");
	}

}
