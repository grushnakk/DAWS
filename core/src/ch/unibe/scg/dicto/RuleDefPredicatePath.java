package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.MultiStringAcceptor;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;

import static ch.unibe.scg.dicto.Constants.*;

public class RuleDefPredicatePath extends Path {

	public RuleDefPredicatePath() {
		super(new MultiStringAcceptor("can only", "cannot", "can", "must")); // TODO magic values
	}

	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		String predicate = result.getRegion(REGION_PREDICATE);
		env.writeCache(CACHE_PREDICATE, predicate);
		return new Next(ID_RULE);
	}

	@Override
	public List<String> suggestions(Environment env) {
		return new ArrayList<>(); //TODO actually return something
	}
}
