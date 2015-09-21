package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.states2.AfterSuccessAction;
import ch.unibe.scg.dicto.states2.StateResult;

public class StoreVarNameAction implements AfterSuccessAction {

	@Override
	public StateResult apply(StateResult result, Environment env) {
		String name = result.getAcceptorResult().getRegion(Dicto.REGION_ID);
		//CHECK IF VAR ALREADY EXISTS
		if(env.isVariableDefined(name))
			return new StateResult("variable already defined: " + name);
		//OK, ITS NEW SO LETS CACHE IT
		env.writeCache(Dicto.CACHE_NEW_VAR_NAME, name);
		return result;
	}

}
