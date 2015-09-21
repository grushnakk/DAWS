package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.states2.AfterSuccessAction;
import ch.unibe.scg.dicto.states2.StateResult;

public class ArgNameCheckerAction implements AfterSuccessAction {

	@Override
	public StateResult apply(StateResult result, Environment env) {
		String argName = result.getAcceptorResult().getRegion(Dicto.REGION_ARG_NAME);
		VariableType type = env.getVariableType(env.readCache(Dicto.CACHE_TYPE));
		if(!type.hasArgument(argName))
			return new StateResult(type.getName() + " does not has the argument: " + argName);
		if(env.hasCached(argName))
			return new StateResult("argument " + argName + " is already defined");
		env.writeCache(Dicto.CACHE_ARG_NAME, argName);
		return result;
	}

}
