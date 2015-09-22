package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.states2.AfterSuccessAction;
import ch.unibe.scg.dicto.states2.StateResult;

public class VarSameTypeCheckerAction implements AfterSuccessAction {

	@Override
	public StateResult apply(StateResult result, Environment env) {
		VariableType type = env.getVariableType(env.readCache(Dicto.CACHE_TYPE));
		String varName = result.getAcceptorResult().getRegion(Dicto.REGION_ID);
		if(!env.isVariableDefined(varName))
			return new StateResult("unknown variable " + varName);
		Variable var = env.getVariable(varName);
		if(!var.getVariableType().equals(type))
			return new StateResult("subjects must be of the same type");
		return result;
	}

}
