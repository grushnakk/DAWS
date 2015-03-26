package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_DIGITS;
import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_LETTERS;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.range;
import static ch.unibe.scg.dicto.parser.Acceptors.string;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.Context;
import ch.unibe.scg.dicto.states.LangError;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.NextAction;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.State;
import ch.unibe.scg.dicto.states.StateMachine;
import ch.unibe.scg.dicto.states.StateResult;
import ch.unibe.scg.dicto.states.SuggestAction;

public class StateExample {

	public static void main(String[] args) {
		State state_0 = new State (
				new Path(
				range(RANGE_LETTERS + RANGE_DIGITS + "_").repeat().region("VAR_NAME")
				.chain(optionalWhitespace(), string(":"), optionalWhitespace()),
				new NextAction() {
					
					@Override
					public StateResult onNext(Environment env, AcceptorResult result) {
						String name = result.getRegion("VAR_NAME");
						if(env.isVariableDefined(name))
							return new LangError("Variable already defined: " + name);
						env.writeCache("VAR_NAME", name);
						return new Next(1); //switch to state with id 1
					}
				},
				new SuggestAction() {
					
					@Override
					public List<String> suggestions(Environment environment) {
						return new ArrayList<>(); //do i want to suggest ":"
					}
				})
		);
		
		StateMachine stateMachine = new StateMachine(new Context("hallo :"), null, 0);
		stateMachine.addState(0, state_0);
	}
}
