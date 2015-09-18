package ch.unibe.scg.dicto.states2;

import java.util.HashMap;
import java.util.Map;

public class StateMachineBuilder {
	
	private final Map<String, State> refs;
	private final Map<String, StateBuilder> stateBuilders;
	
	public StateMachineBuilder() {
		this.refs = new HashMap<String, State>();
		this.stateBuilders = new HashMap<String, StateBuilder>();
	}

	public StateBuilder state(String name) {
		if(!stateBuilders.containsKey(name)) {
			StateBuilder stateBuilder = new StateBuilder(this);
			stateBuilder.setName(name);
			stateBuilders.put(name, stateBuilder);
		}
		return stateBuilders.get(name);
	}
	
	public State ref(String name) {
		if(!refs.containsKey(name))
			refs.put(name, new StateRef(name));
		return refs.get(name);
	}
	
	public StateMachine build() {
		/*
		 * build all states
		 */
		Map<String, State> states = new HashMap<String, State>();
		for(String name : stateBuilders.keySet()) {
			states.put(name, stateBuilders.get(name).build());
		}
		
		/*
		 * iterate all paths and replace references
		 */
		for(State state : states.values()) {
			for(Path path : state.getPaths()) {
				/*
				 * this looks confusing:
				 * I get the StateRef from every path and look up the real State
				 */
				path.setDestination(states.get(path.getDestination().getName()));
			}
		}
		//TODO real StateMachine
		return null;
	}
}
