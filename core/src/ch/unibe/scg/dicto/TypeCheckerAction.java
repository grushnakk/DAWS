package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.states2.AfterSuccessAction;
import ch.unibe.scg.dicto.states2.StateResult;

public class TypeCheckerAction implements AfterSuccessAction {

	@Override
	public StateResult apply(StateResult result, Environment env) {
		String typeName = result.getAcceptorResult().getRegion(Dicto.REGION_TYPE);
		if(!env.isTypeDefined(typeName))
			return new StateResult("unkown type: " + typeName);
		env.writeCache(Dicto.CACHE_TYPE, typeName);
		return result;
	}

}
