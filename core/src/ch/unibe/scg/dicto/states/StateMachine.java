package ch.unibe.scg.dicto.states;

import ch.unibe.scg.dicto.parser.Context;

public class StateMachine {

	private State active;
	private final Context context;
	private final Environment environment;
	
	public StateMachine(State start, Context context, Environment environment) {
		this.active = start;
		this.context = context;
		this.environment = environment;
	}
	
	public void setActiveState(State state) {
		this.active = state;
	}
	
	public Context getContext() {
		return context;
	}
	
	public Environment getEnvironment() {
		return environment;
	}
	
	public State getActiveState() {
		return active;
	}
	
	public StateResult run() { //TODO better name
		StateResult result = null;
		do {
			result = active.process(context, environment);
		} while(result.isNext());
		return result;
	}
}
