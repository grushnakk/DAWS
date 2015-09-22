package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Predicate;
import ch.unibe.scg.dicto.states2.Suggestor;

public class PredicateSuggestor implements Suggestor {

	@Override
	public List<String> suggestions(Environment env) {
		List<String> predicates = new ArrayList<String>();
		for(Predicate predicate : Predicate.values()) {
			if(predicate == Predicate.ONLY_CAN)
				continue;
			predicates.add(predicate.getCode());
		}
		return predicates;
	}

}
