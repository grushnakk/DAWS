package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;
import static ch.unibe.scg.dicto.Constants.*;
import static ch.unibe.scg.dicto.parser.Acceptors.negRange;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.string;

public class VarDefArgStringPath extends Path {

	public VarDefArgStringPath() {
		super(string("\"").chain(negRange("\"").repeat().region(REGION_STRING_CONTENT), string("\""), optionalWhitespace()));
	}

	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		String content = result.getRegion(REGION_STRING_CONTENT);
		String argName = env.readCache(CACHE_ARG_NAME);
		env.writeCache(argName, content);
		return new Next(ID_AFTER_ARG_VALUE);
	}

	@Override
	public List<String> suggestions(Environment env) {
		return new ArrayList<String>();
	}

}
