package ch.unibe.scg.dicto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {

	private final List<Rule> rules;
	private final List<Variable> variables;
	private final List<VariableType> variableTypes;
	
	private Map<String, String> cache;
	
	public Environment(List<Rule> rules, List<Variable> variables, List<VariableType> variableTypes) {
		this.rules = Collections.unmodifiableList(rules);
		this.variables = Collections.unmodifiableList(variables);
		this.variableTypes = Collections.unmodifiableList(variableTypes);
		this.cache = new HashMap<String, String>();
	}
	
	public Environment copy() {
		return new Environment(rules, variables, variableTypes);
	}
	
	public boolean isTypeDefined(String typeName) {
		for(VariableType type : variableTypes)
			if(type.getName().equals(typeName))
				return true;
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
	
	public void resetCache() {
		cache = new HashMap<String, String>();
	}
	
	public List<Variable> getVariables() {
		return null; //TODO method stub
	}
	
	
	public boolean isVariableDefined(String varName) {
		return false; //TODO method stub
	}
}
