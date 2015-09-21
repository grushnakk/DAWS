package ch.unibe.scg.dicto.states2;

import ch.unibe.scg.dicto.model.Environment;

public interface AfterSuccessAction {
	
	public static AfterSuccessAction NO_ACTION = new AfterSuccessAction() {
		
		@Override
		public StateResult apply(StateResult result, Environment env) {return result;}
	};

	public StateResult apply(StateResult result, Environment env);
}
