package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.Constants.CACHE_VAR_NAME;
import static ch.unibe.scg.dicto.Constants.ID_ACC;
import static ch.unibe.scg.dicto.Constants.ID_PREDICATE;
import static ch.unibe.scg.dicto.Constants.REGION_IDENTIFIER;
import static ch.unibe.scg.dicto.parser.Acceptors.whitespace;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.states.LangError;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;

public class RuleDefStartPath extends Path {

	public RuleDefStartPath() {
		super(ID_ACC.region(REGION_IDENTIFIER).chain(whitespace()));
	}
	
	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		String varName = result.getRegion(REGION_IDENTIFIER);
		if (env.isVariableDefined(varName)) {
			env.writeCache(CACHE_VAR_NAME, varName);
			return new Next(ID_PREDICATE);
		}
		return new LangError("unknown Variable " + varName);
	}

	//TODO actual return something
	@Override
	public List<String> suggestions(Environment env) {
		return new ArrayList<>();
	}
}
