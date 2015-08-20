package ch.unibe.scg.dicto.model;

import java.util.Collections;
import java.util.List;

public class Rule implements Named {
	
	private final String name;
	private List<Predicate> predicates;

	public Rule(final String name, List<Predicate> predicates) {
		this.name = name;
		this.predicates = Collections.unmodifiableList(predicates);		
	} 
	
	public boolean canBeUsed(Predicate predicate) {
		for(Predicate p : predicates) {
			if(p.equals(predicate))
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
