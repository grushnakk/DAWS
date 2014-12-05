package ch.unibe.scg.dicto.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class DictoConfiguration {
	protected final Set<DictoAdapter> adapters;
	protected final DictoVariableDefinitionStatement variableDefinitionStatement; //better name

	public DictoConfiguration(DictoVariableDefinitionStatement varDefStatement, Set<DictoAdapter> adapters) {
		this.variableDefinitionStatement = varDefStatement;
		this.adapters = new HashSet<>(adapters);
	}
	
	public DictoConfiguration(DictoVariableDefinitionStatement varDefStatement, DictoAdapter... adapters) {
		this.variableDefinitionStatement = varDefStatement;
		this.adapters = new HashSet<>();
		for(DictoAdapter adapter : adapters)
			this.adapters.add(adapter);
	}
	
	public Iterator<DictoAdapter> adapters() {
		return adapters.iterator();
	}
	
	public DictoVariableDefinitionStatement getVariableDefinitionStatement() {
		return variableDefinitionStatement;
	}
}
