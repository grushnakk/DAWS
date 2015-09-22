package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.states2.AfterSuccessAction;
import ch.unibe.scg.dicto.states2.StateResult;

public class StoreRuleAction implements AfterSuccessAction {

	@Override
	public StateResult apply(StateResult result, Environment env) {
		String actualRule = result.getAcceptorResult().getRegion(Dicto.REGION_RULE);
		VariableType type = env.getVariableType(env.readCache(Dicto.CACHE_TYPE));
		for(Rule rule : type.getRules()) {
			if(rule.getName().equals(actualRule)) {
				env.writeCache(Dicto.CACHE_RULE, actualRule);
				return result;
			}
		}
		return new StateResult("rule " + actualRule + " is not supported by type" + type.getName());
	}

}
