package ch.unibe.scg.dicto;

public class Argument {
	
	protected final boolean optional;
	protected final String name;
	protected final boolean multiple;
	
	public Argument(String name, boolean optional, boolean multiple) {
		this.optional = optional;
		this.name = name;
		this.multiple = multiple;
	}

	public boolean isOptional() {
		return optional;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		if(optional && multiple)
			return name + "*";
		else if(!optional && multiple)
			return name + "+";
		else if(optional && !multiple)
			return name + "?";
		else
			return name;
	}
}
