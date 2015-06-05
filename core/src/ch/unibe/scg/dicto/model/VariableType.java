package ch.unibe.scg.dicto.model;

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
}
