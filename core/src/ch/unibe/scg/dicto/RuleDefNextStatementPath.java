package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.MultiStringAcceptor;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;

public class RuleDefNextStatementPath extends Path {

	public RuleDefNextStatementPath() {
		super(new MultiStringAcceptor("\n", "\r"));
	}
	
	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		/*
		 * we only need to rest the cache, because rules don't need to be stored for autocomplete
		 */
		env.resetCache();
		return new Next(Constants.ID_START);
	}

	@Override
	public List<String> suggestions(Environment env) {
		return new ArrayList<>();
	}

}
