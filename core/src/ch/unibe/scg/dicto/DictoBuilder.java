package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.*;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.states2.StateMachine;
import ch.unibe.scg.dicto.states2.StateMachineBuilder;

public class DictoBuilder {

	/*
	 * the names of the states
	 */
	private static final String STATE_START = "Start";
	private static final String STATE_VAR_TYPE = "VarType";
	private static final String STATE_ID_AFTER_ONLY = "AfterOnly";
	private static final String STATE_RULE = "Rule";
	/*
	 * some other stuff
	 */
	private static final Acceptor idAcceptor = range(RANGE_DIGITS + RANGE_LETTERS).repeat();
	
	public StateMachine build() {
		StateMachineBuilder smBuilder = new StateMachineBuilder();
		smBuilder.startAt(STATE_START); // where do we start :D

		smBuilder.state(STATE_START)
						.pathTo(STATE_VAR_TYPE)
						.accepts(idAcceptor.chain(optionalWhitespace(), string("=")))
						.suggestsNothing() // this is the default, so no reason to call actually ;D
						.complete();
		smBuilder.state(STATE_START)
						.pathTo(STATE_ID_AFTER_ONLY)
						.accepts(string("only"))
						.suggests("only")
						.complete();
		smBuilder.state(STATE_START)
						.pathTo(STATE_RULE)
						.accepts(idAcceptor)
						//TODO suggests all existing variables
						.complete();
		return smBuilder.build();
	}
}
