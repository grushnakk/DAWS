package ch.unibe.scg.dicto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VariableType implements Named {

	private final String name;
	
	private final List<Argument> arguments;
	
	private final List<String> rules;
	
	public VariableType(String name, List<Argument> arguments, String... ruleArray) {
		this.name = name;
		this.arguments = Collections.unmodifiableList(arguments);
		this.rules = new ArrayList<>();
		for(String s : ruleArray)
			rules.add(s);
	}
	
	@Override
	public String getName() {
		return name; 
	}
	
	public List<Argument> getArguments() {
		return new ArrayList<Argument>(arguments);
	}
	
	public boolean hasArgument(String argName) {
		for(Argument arg : arguments)
			if(arg.getName().equals(argName))
				return true;
		return false;
	}
	
	public List<String> getRules() {
		return new ArrayList<String>(rules);
	}
}
