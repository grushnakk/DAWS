package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.states2.Suggestor;

public class RuleSuggestor implements Suggestor {

	@Override
	public List<String> suggestions(Environment env) {
		VariableType type = env.getVariableType(env.readCache(Dicto.CACHE_TYPE));
		List<String> suggestions = new ArrayList<String>();
		for(Rule rule : type.getRules()) {
			suggestions.add(rule.getName());
		}
		return suggestions;
	}
}
