package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.MultiStringAcceptor;
import ch.unibe.scg.dicto.states.LangError;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;
import static ch.unibe.scg.dicto.Constants.*;

public class VarDefTypePath extends Path {

	public VarDefTypePath(Environment env) {
		super(buildTypeAcceptor(env));
	}

	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		String varType = result.getRegion(REGION_IDENTIFIER);
		if (env.isTypeDefined(varType)) {
			env.writeCache(CACHE_VAR_TYPE, varType);
			return new Next(ID_KEYWORD_WITH);
		} else {
			return new LangError("unknown variable type: " + varType);
		}
	}
	
	@Override
	public List<String> suggestions(Environment environment) {
		List<String> suggestions = new ArrayList<>();
		for (VariableType type : environment.getVariableTypes())
			suggestions.add(type.getName());
		return suggestions;
	}
	
	private static Acceptor buildTypeAcceptor(Environment env) {
		List<String> types = new ArrayList<>();
		for (VariableType type : env.getVariableTypes())
			types.add(type.getName());
		return new MultiStringAcceptor(types).region(REGION_IDENTIFIER);
	}
}
