package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.states2.AfterSuccessAction;
import ch.unibe.scg.dicto.states2.StateResult;

public class StorePredicateAction implements AfterSuccessAction {

	@Override
	public StateResult apply(StateResult result, Environment env) {
		String pred = result.getAcceptorResult().getRegion(Dicto.REGION_PREDICATE);
		env.writeCache(Dicto.CACHE_PREDICATE, pred);
		return result;
	}

}
