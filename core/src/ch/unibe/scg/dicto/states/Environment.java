package ch.unibe.scg.dicto.states;

import java.util.Collections;
import java.util.List;

import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;

public class Environment {

	private final List<Rule> rules;
	private final List<Variable> variables;
	private final List<VariableType> variableTypes;
	
	public Environment(List<Rule> rules, List<Variable> variables, List<VariableType> variableTypes) {
		this.rules = Collections.unmodifiableList(rules);
		this.variables = Collections.unmodifiableList(variables);
		this.variableTypes = Collections.unmodifiableList(variableTypes);
	}
	
	public Environment copy() {
		return new Environment(rules, variables, variableTypes);
	}
}
