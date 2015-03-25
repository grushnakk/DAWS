package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_DIGITS;
import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_LETTERS;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.range;
import static ch.unibe.scg.dicto.parser.Acceptors.string;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.State;

public class StateExample {

	public static void main(String[] args) {
		State state = new State (
				new Path(
				range(RANGE_LETTERS + RANGE_DIGITS + "_").repeat().region("VAR_NAME")
				.chain(optionalWhitespace(), string(":"), optionalWhitespace()),
				null,
				null
				)
		);
	}
}
