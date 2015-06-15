package ch.unibe.scg.dicto.model;

import java.util.ArrayList;
import java.util.List;

public class Rule implements Named {
	
	private final String name;
	private List<Pair> pairs;

	public Rule(final String name, Pair... pairs) {
		this.name = name;
		this.pairs = new ArrayList<>();
		for(Pair pair : pairs)
			this.pairs.add(pair);
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
	
	public static class Pair {
		public VariableType type;
		public Predicate predicate;
		
		public Pair(VariableType type, Predicate predicate) {
			this.type = type;
			this.predicate = predicate;
		}
		
		boolean match(VariableType type, Predicate predicate) {
			return this.type.equals(type) && predicate == this.predicate;
		}
	}
}
