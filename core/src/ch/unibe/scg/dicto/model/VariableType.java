package ch.unibe.scg.dicto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VariableType implements Named {

	private final String name;
	
	private final List<Argument> arguments;
	
	public VariableType(String name, List<Argument> arguments) {
		this.name = name;
		this.arguments = Collections.unmodifiableList(arguments);
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
}
