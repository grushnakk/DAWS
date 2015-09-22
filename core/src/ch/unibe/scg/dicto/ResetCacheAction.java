package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.states2.AfterSuccessAction;
import ch.unibe.scg.dicto.states2.StateResult;

public class ResetCacheAction implements AfterSuccessAction {

	@Override
	public StateResult apply(StateResult result, Environment env) {
		env.resetCache();
		return result;
	}

}
