package ch.unibe.scg.dicto.model;

import java.util.Collections;
import java.util.List;

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
	
	public boolean isVariableDefined(String varName) {
		return false; //TODO method stub
	}
	
	public void writeCache(String key, String value) {
		//TODO method stub
	}
	
	public String readCache(String key) {
		return ""; //TODO method stub
	}
	
	public void resetCache() {
		//TODO method stub6
	}
}
