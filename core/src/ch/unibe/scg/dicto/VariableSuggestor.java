package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.states2.Suggestor;

public class VariableSuggestor implements Suggestor {

	@Override
	public List<String> suggestions(Environment env) {
		List<String> varNames = new ArrayList<>();
		for(Variable var : env.getVariables()) {
			varNames.add(var.getName());
		}
		return varNames;
	}

}
