package ch.unibe.scg.dicto.states2;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.Context;

public class StateMachine {

	private final State start;
	
	public StateMachine(State start) {
		this.start = start;
	}
	
	public StateMachineResult run(Context context, Environment env) {
		State current = start;
		StateResult result;
		do {
			result = current.accept(context);
			switch(result.getAcceptorResult().getResultType()) {
			case FAILURE:
				return new StateMachineError("unexpected token at: " + context.getCurrentIndex());
			case INCOMPLETE:
				return new StateMachineSuccess(current.suggestions(env));
			case SUCCESS:
				//TODO we want to do something here
				current = result.getPath().getDestination();
				break;
			}
		} while(true);
	}
}
