package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.states2.Suggestor;

public class VarTypeSuggestor implements Suggestor {

	@Override
	public List<String> suggestions(Environment env) {
		List<String> suggestions = new ArrayList<String>();
		for(VariableType type : env.getVariableTypes())
			suggestions.add(type.getName());
		return suggestions;
	}

}
