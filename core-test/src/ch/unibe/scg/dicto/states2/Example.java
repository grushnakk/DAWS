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
		sMachine.state("Start")
				.pathTo("Var_Type")
				.accepts(idAcceptor)
				.suggestsNothing()
				.complete();
		sMachine.state("Start")
				.pathTo("Id_After_Only")
				.accepts(string("only"))
				.suggests("only")
				.complete();
	}
}
