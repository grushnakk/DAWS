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
}
