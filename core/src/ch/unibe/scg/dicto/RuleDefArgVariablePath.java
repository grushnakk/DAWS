package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;

import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;

import static ch.unibe.scg.dicto.Constants.*;

public class RuleDefArgVariablePath extends Path {

	public RuleDefArgVariablePath() {
		super(ID_ACC.chain(optionalWhitespace()));
	}
	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		return null;
	}

	@Override
	public List<String> suggestions(Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

}
