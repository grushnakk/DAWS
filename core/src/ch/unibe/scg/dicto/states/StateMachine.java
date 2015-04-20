package ch.unibe.scg.dicto.states;

import java.util.HashMap;
import java.util.Map;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.Context;

public class StateMachine {

	private int activeStateID;
	private Map<Integer, State> stateMap;
	private final Context context;
	private final Environment environment;
	private boolean completed;
	private StateResult result;
	
	public StateMachine(Context context, Environment environment, int activeStateID, Map<Integer, State> stateMap) {
		this.stateMap = new HashMap<Integer, State>(stateMap);
		this.context = context;
		this.environment = environment;
		this.activeStateID = activeStateID;
	}
	
	public StateMachine(int activeStateID) {
		this(null, null, activeStateID, new HashMap<Integer, State>());
	}
	
	public StateMachine(Context context, Environment environment, int activeStateID) {
		this(context, environment, activeStateID, new HashMap<Integer, State>());
	}
	
	private StateMachine(StateMachine sm, Context context, Environment env) {
		this.activeStateID = sm.activeStateID;
		this.stateMap = new HashMap<>(sm.stateMap);
		this.context = context;
		this.environment = env;
	}
	
	public StateMachine clone(Environment environment, Context context) {
		return new StateMachine(this, context, environment);
	}
	
	public void setActiveStateID (int stateID) {
		this.activeStateID = stateID;
	}
	
	public void addState(int stateID, State state) {
		stateMap.put(stateID, state);
	}
	
	public boolean hasState(int stateID) {
		return stateMap.containsKey(stateID);
	}
	
	public State resolveStateID(int stateID) {
		return stateMap.get(stateID);
	}
	
	public Context getContext() {
		return context;
	}
	
	public Environment getEnvironment() {
		return environment;
	}
	
	public int getActiveStateID() {
		return activeStateID;
	}
	
	public void run() { //TODO better name
		if(isCompleted())
			throw new IllegalStateException("every StateMachine can only be run once.");
		StateResult result = null;
		do {
			result = resolveStateID(activeStateID).process(context, environment);
			if(result.isNext()) {
				setActiveStateID(result.getNextStateID());
			}
		} while(result.isNext());
		this.result = result;
		this.completed = true;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	/**
	 * null if StateMachine was not run yet.
	 * @return
	 */
	public StateResult getResult() {
		return result;
	}
}
