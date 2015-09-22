package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.states2.AfterSuccessAction;
import ch.unibe.scg.dicto.states2.StateResult;

public class VarLookUpAction implements AfterSuccessAction {

	@Override
	public StateResult apply(StateResult result, Environment env) {
		String name = result.getAcceptorResult().getRegion(Dicto.REGION_ID);
		if(!env.isVariableDefined(name))
			return result;
		VariableType type = env.getVariable(name).getVariableType();
		env.writeCache(Dicto.CACHE_TYPE, type.getName());
		return result;
	}

}
