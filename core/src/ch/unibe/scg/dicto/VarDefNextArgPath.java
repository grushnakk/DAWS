package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.string;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;
import static ch.unibe.scg.dicto.Constants.*;

public class VarDefNextArgPath extends Path {

	
	public VarDefNextArgPath() {
		super(string(",").chain(optionalWhitespace()));
	}

	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		return new Next(ID_ARG_NAME);
	}

	@Override
	public List<String> suggestions(Environment env) {
		return new ArrayList<>();
	}

}
