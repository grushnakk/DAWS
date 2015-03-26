package ch.unibe.scg.dicto.states;

import java.util.List;

import ch.unibe.scg.dicto.model.Environment;

public interface SuggestAction {

	public List<String> suggestions(Environment environment);
}
