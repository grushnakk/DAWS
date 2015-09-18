package ch.unibe.scg.dicto.states2;

import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_DIGITS;
import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_LETTERS;
import static ch.unibe.scg.dicto.parser.Acceptors.string;
import static ch.unibe.scg.dicto.parser.Acceptors.range;
import ch.unibe.scg.dicto.parser.Acceptor;

public final class Example {

	public static void main(String[] args) {
		Acceptor idAcceptor = range(RANGE_DIGITS + RANGE_LETTERS).repeat();
		StateMachineBuilder sMachine = new StateMachineBuilder();
		sMachine.state("STATE_START")
				.pathTo("PATH_UNKNOWN_ID")
				.accepts(idAcceptor)
				.suggestsNothing()
				.complete();
		sMachine.state("STATE_START")
				.pathTo("PATH_ONLY")
				.accepts(string("only"))
				.suggests("only")
				.complete();
		StateMachine stateMachine = sMachine.build();
	}
}
