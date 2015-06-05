package ch.unibe.scg.dicto.model;

public class Argument implements Named{

	private final String name;
	
	public Argument(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
