package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.Constants.REGION_STRING_CONTENT;
import static ch.unibe.scg.dicto.parser.Acceptors.negRange;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.string;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;

public class RuleDefArgStringPath extends Path {

	public RuleDefArgStringPath() {
		super(string("\"").chain(negRange("\"").repeat().region(REGION_STRING_CONTENT), string("\""), optionalWhitespace()));
	}

	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		System.out.println(result.getRegion(REGION_STRING_CONTENT));
		return new Next(Constants.ID_RULE_AFTER_ARG);
	}

	@Override
	public List<String> suggestions(Environment env) {
		return new ArrayList<>();
	}
}
