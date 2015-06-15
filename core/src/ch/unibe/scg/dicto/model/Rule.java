package ch.unibe.scg.dicto.model;

import java.util.List;

public class Rule implements Named {
	
	private final String name;
	private List<Pair> pairs;

	public Rule(final String name) {
		this.name = name;
	} 
	
	public boolean canBeUsed(VariableType type, Predicate predicate) {
		for(Pair pair : pairs) {
			if(pair.match(type, predicate))
				return true;
		}
		return false;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	private static class Pair {
		public VariableType type;
		public Predicate predicate;
		
		boolean match(VariableType type, Predicate predicate) {
			return this.type.equals(type) && predicate == this.predicate;
		}
	}
}
