package ch.unibe.scg.dicto.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DictoVariableType {
	
	protected final String name;
	protected final List<DictoVariableArgument> arguments;
	
	public DictoVariableType(String name, DictoVariableArgument... arguments) {
		this.name = name;
		this.arguments = Arrays.asList(arguments);
	}

	public String getName() {
		return name;
	}
	
	public Iterator<DictoVariableArgument> arguments() {
		return arguments.iterator();
	}
}
