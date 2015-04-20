package ch.unibe.scg.dicto.states;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;

public interface NextAction {

	public StateResult onNext(Environment env, AcceptorResult result);
}
