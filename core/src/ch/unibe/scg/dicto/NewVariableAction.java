package ch.unibe.scg.dicto;

import java.util.HashMap;
import java.util.Map;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.states2.AfterSuccessAction;
import ch.unibe.scg.dicto.states2.StateResult;

public class NewVariableAction implements AfterSuccessAction {

	@Override
	public StateResult apply(StateResult result, Environment env) {
		VariableType type = env.getVariableType(env.readCache(Dicto.CACHE_TYPE));
		String name = env.readCache(Dicto.CACHE_NEW_VAR_NAME);
		Map<String, String> arguments = new HashMap<String, String>();
		//TODO missing arguments
		for(Argument arg : type.getArguments()) {
			if(env.hasCached(arg.getName()))
				arguments.put(arg.getName(), env.readCache(arg.getName()));
		}
		env.addVariable(new Variable(name, type, arguments));
		env.resetCache();
		return result;
	}

}
