package ch.unibe.scg.dicto.states2;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;

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
	public List<String> suggestions(Environment env) {
		return new ArrayList<>(suggestions);
	}

}
