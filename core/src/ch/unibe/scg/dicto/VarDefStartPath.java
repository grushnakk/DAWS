package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.Constants.CACHE_VAR_NAME;
import static ch.unibe.scg.dicto.Constants.ID_ACC;
import static ch.unibe.scg.dicto.Constants.ID_TYPE;
import static ch.unibe.scg.dicto.Constants.REGION_IDENTIFIER;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.string;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;

class VarDefStartPath extends Path{

	public VarDefStartPath() {
		super(ID_ACC.region(REGION_IDENTIFIER).chain(optionalWhitespace(), string("="), optionalWhitespace()));
	}
	
	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		env.writeCache(CACHE_VAR_NAME, result.getRegion(REGION_IDENTIFIER));
		return new Next(ID_TYPE);
	}

	@Override
	public List<String> suggestions(Environment env) {
		return new ArrayList<>();
	}
}
