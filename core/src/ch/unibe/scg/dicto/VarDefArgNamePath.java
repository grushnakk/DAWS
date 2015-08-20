package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.string;
import static ch.unibe.scg.dicto.Constants.*;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.states.LangError;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;

public class VarDefArgNamePath extends Path {

	public VarDefArgNamePath() {
		super(ID_ACC.region(REGION_IDENTIFIER).chain(optionalWhitespace(), string(":"), optionalWhitespace()));
	}

	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		VariableType type = env.getVariableType(env.readCache(CACHE_VAR_TYPE));
		String argName = result.getRegion(REGION_IDENTIFIER);
		if (!type.hasArgument(argName)) {
			return new LangError("unknown argument for type " + type + ": " + argName);
		}
		env.writeCache(CACHE_ARG_NAME, argName);
		return new Next(ID_ARG_VALUE);
	}

	@Override
	public List<String> suggestions(Environment env) {
		String typeName = env.readCache(CACHE_VAR_TYPE);
		VariableType type = env.getVariableType(typeName);
		List<String> names = new ArrayList<>();
		for (Argument arg : type.getArguments()) {
			names.add(arg.getName());
		}
		return names;
	}

}
