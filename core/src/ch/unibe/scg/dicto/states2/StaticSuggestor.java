package ch.unibe.scg.dicto.states2;

import java.util.ArrayList;
import java.util.List;

public class StaticSuggestor implements Suggestor {
	
	private final List<String> suggestions;

	public StaticSuggestor(List<String> suggestions) {
		this.suggestions = new ArrayList<>(suggestions);
	}
	
	public StaticSuggestor(String element) {
		this.suggestions = new ArrayList<>();
		this.suggestions.add(element);
	}
	
	@Override
	public List<String> suggestions() {
		return new ArrayList<>(suggestions);
	}

}
