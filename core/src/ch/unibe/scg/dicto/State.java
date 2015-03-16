package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

public class State {
	
	public List<StateAction> actions;
	
	public State(StateAction... actions) {
		this.actions = new ArrayList();
		for(StateAction a : actions)
			this.actions.add(a);
	}
	
	public void process(StateMachine stateMachine) {
		for(StateAction action : actions) {
			
		}
	}
}
