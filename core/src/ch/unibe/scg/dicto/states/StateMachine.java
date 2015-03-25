package ch.unibe.scg.dicto.states;

import java.util.HashMap;
import java.util.Map;

import ch.unibe.scg.dicto.parser.Context;

public class StateMachine {

	private State active;
	private Map<Integer, State> stateMap;
	private final Context context;
	private final Environment environment;
	
	public StateMachine(Context context, Environment environment, int activeStateID, Map<Integer, State> stateMap) {
		this.stateMap = new HashMap<Integer, State>(stateMap);
		this.context = context;
		this.environment = environment;
		setStateToActive(activeStateID);
	}
	
	public StateMachine(Context context, Environment environment, int activeStateID) {
		this(context, environment, activeStateID, new HashMap<Integer, State>());
	}
	
	public void setStateToActive(int stateID) { //TODO long name
		this.active = stateMap.get(stateID);
	}
	
	public void addState(int stateID, State state) {
		stateMap.put(stateID, state);
	}
	
	public boolean hasState(int stateID) {
		return stateMap.containsKey(stateID);
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
