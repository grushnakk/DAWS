package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.states2.Suggestor;

public class ArgNameSuggestor implements Suggestor {

	@Override
	public List<String> suggestions(Environment env) {
		List<String> suggestions = new ArrayList<>();
		VariableType type = env.getVariableType(env.readCache(Dicto.CACHE_TYPE));
		for(Argument arg : type.getArguments()) {
			if(!env.hasCached(arg.getName()))
				suggestions.add(arg.getName());
		}
		return suggestions;
	}

}
