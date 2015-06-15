package ch.unibe.scg.dicto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {
	private final List<Variable> variables;
	private final List<VariableType> variableTypes;
	private final List<Rule> rules;
	
	private Map<String, String> cache;
	
	public Environment(List<Variable> variables, List<VariableType> variableTypes, List<Rule> rules) {
		this.variables = new ArrayList<>(variables);
		this.variableTypes = Collections.unmodifiableList(variableTypes);
		this.rules = Collections.unmodifiableList(rules);
		this.cache = new HashMap<String, String>();
	}
	
	public Environment copy() {
		return new Environment(variables, variableTypes, rules);
	}
	
	public boolean isTypeDefined(String typeName) {
		for(VariableType type : variableTypes)
			if(type.getName().equals(typeName))
				return true;
		return false;
	}
	
	public boolean isVariableDefined(String varName) {
		for(Variable variable : variables) {
			if(variable.getName().equals(varName))
				return true;
		}
		return false;
	}
	
	public List<VariableType> getVariableTypes()  {
		return new ArrayList<>(variableTypes);
	}
	
	public void writeCache(String key, String value) {
		cache.put(key, value);
	}
	
	public String readCache(String key) {
		return cache.get(key);
	}
	
	public boolean hasCached(String key) {
		return cache.containsKey(key);
	}
	
	public void resetCache() {
		cache = new HashMap<String, String>();
	}
	
	public List<Variable> getVariables() {
		return new ArrayList<>(variables);
	}
	
	public VariableType getVariableType(String varType) {
		for(VariableType type : variableTypes)
			if(type.getName().equals(varType))
				return type;
		return null;
	}
	
	public void addVariable(Variable variable) {
		variables.add(variable);
	}
}
