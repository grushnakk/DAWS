package ch.unibe.scg.dicto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VariableType implements Named {

	private final String name;
	
	private final List<Argument> arguments;
	
	private final List<Rule> rules;
	
	public VariableType(String name, List<Argument> arguments, List<Rule> rules) {
		this.name = name;
		this.arguments = Collections.unmodifiableList(arguments);
		this.rules = Collections.unmodifiableList(rules);
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
	
	public List<Rule> getRules() {
		return new ArrayList<>(rules);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VariableType other = (VariableType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
