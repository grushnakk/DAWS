package ch.unibe.scg.dicto.model;

public class DictoVariableArgument {

	protected final String name;
	protected final boolean optional;
	protected final boolean multiple;
	//TODO return type
	
	public DictoVariableArgument(String name, boolean optional, boolean multiple) {
		this.name = name;
		this.optional = optional;
		this.multiple = multiple;
	}
	
	public String getName() {
		return name;
	}
}
