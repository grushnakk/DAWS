package ch.unibe.scg.dicto.model;

import java.util.Collections;
import java.util.Map;

public class Variable {
	
	private final String name;
	private final VariableType type;
	private final Map<String, String> arguments;
	
	public Variable(String name, VariableType type, Map<String, String> arguments) {
		this.name = name;
		this.type = type;
		this.arguments = Collections.unmodifiableMap(arguments);
	}
	
	public String getName() {
		return name; 
	}

	public VariableType getVariableType() {
		return type;
	}
	
	public String getArgument(String argName) {
		return arguments.get(argName);
	}
}
