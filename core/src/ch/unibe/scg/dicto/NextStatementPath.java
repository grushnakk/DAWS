package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.MultiStringAcceptor;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;
import static ch.unibe.scg.dicto.Constants.*;

public class NextStatementPath extends Path {

	public NextStatementPath() {
		super(new MultiStringAcceptor("\n", "\r"));
	}

	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		String nameCache = env.readCache(CACHE_VAR_NAME);
		String typeCache = env.readCache(CACHE_VAR_TYPE);
		VariableType varType = env.getVariableType(typeCache);
		Map<String, String> args = new HashMap<>();
		for(Argument arg : varType.getArguments()) {
			if(env.hasCached(arg.getName()))
				args.put(arg.getName(), env.readCache(arg.getName()));
		}
		Variable var = new Variable(nameCache, varType, args);
		env.addVariable(var);
		env.resetCache();
		return new Next(ID_START);
	}

	@Override
	public List<String> suggestions(Environment env) {
		return new ArrayList<>();
	}

}
