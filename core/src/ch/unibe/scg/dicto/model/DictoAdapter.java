package ch.unibe.scg.dicto.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DictoAdapter {

	protected final Set<DictoVariableType> variableTypes;
	
	public DictoAdapter(Set<DictoVariableType> variableTypes) {
		this.variableTypes = new HashSet<>(variableTypes);
	}
	
	public DictoAdapter(DictoVariableType... variableTypes) {
		this.variableTypes = new HashSet<>();
		for(DictoVariableType variableType : variableTypes)
			this.variableTypes.add(variableType);
	}
	
	public Iterator<DictoVariableType> variableTypes() {
		return variableTypes.iterator();
	}
}
