package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.states2.AfterSuccessAction;
import ch.unibe.scg.dicto.states2.StateResult;

public class VarCheckerAction implements AfterSuccessAction {

	@Override
	public StateResult apply(StateResult result, Environment env) {
		String varName = result.getAcceptorResult().getRegion(Dicto.REGION_PARAMETER);
		if(!env.isVariableDefined(varName))
			return new StateResult("unknown variable " + varName);
		return result;
	}

}
