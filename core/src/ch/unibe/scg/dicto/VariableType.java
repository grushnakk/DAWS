package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

public class VariableType {

	protected final String name;
	protected final List<Argument> arguments;
	
	public VariableType(String name, Argument... arguments) {
		this.name = name;
		this.arguments = new ArrayList<>();
		for(Argument argument: arguments) {
			this.arguments.add(argument);
		}
	}
	public String getName() {
		return name;
	}
	
	public List<Argument> getArguments() {
		return new ArrayList<>(arguments);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("name");
		builder.append("(");
		for(Argument argument : arguments) {
			builder.append(argument).append(", ");
		}
		return builder.toString();
	}
}
