package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;
import static ch.unibe.scg.dicto.parser.Acceptors.string;
import static ch.unibe.scg.dicto.parser.Acceptors.whitespace;;

public class RuleOnlyPath extends Path {
	
	public RuleOnlyPath() {
		super(string("only").chain(whitespace()));
	}

	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		env.writeCache(Constants.CACHE_ONLY_CAN, "");
		return new Next(Constants.ID_RULE_ONLY_VAR);
	}

	@SuppressWarnings("serial")
	@Override
	public List<String> suggestions(Environment env) {
		return new ArrayList<String>(){{add("only");}};
	}

}
