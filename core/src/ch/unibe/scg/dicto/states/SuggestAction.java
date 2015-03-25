package ch.unibe.scg.dicto.states;

import java.util.List;

public interface SuggestAction {

	public List<String> suggestions(Environment environment);
}
