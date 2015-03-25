package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.*;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.states.Environment;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.State;
import ch.unibe.scg.dicto.states.StateResult;

public class StateExample {

	public static void main(String[] args) {
		State state = new State (new Path(
				range(RANGE_LETTERS + RANGE_DIGITS + "_").repeat().region("VAR_NAME")
				.chain(optionalWhitespace(), string(":"), optionalWhitespace())) {
			
			@Override
			public List<String> suggestions(Environment env) {
				return new ArrayList<>(); //empty because we can't see in the user's mind :)
			}
			
			@Override
			public StateResult onSuccess(Environment env, AcceptorResult result) {
				//TODO check if var_name is already taken
				//TODO cache var_name
				return new Next(null); //TODO specify next state
			}
		});
	}
}
