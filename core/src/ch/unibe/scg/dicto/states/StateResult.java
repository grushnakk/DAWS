package ch.unibe.scg.dicto.states;

public class StateResult {

	public static enum Type {
		INCOMPLETE, FAILURE, SUCCESS;
	}
	
	public State nextState;
	public Type type;
	public String errorMessage;
	
	public static StateResult success(State nextState) {
		StateResult result = new StateResult();
		result.type = Type.SUCCESS;
		result.nextState = nextState;
		return result;
	}
	
	public static StateResult failure() {
		StateResult result = new StateResult();
		result.type = Type.FAILURE;
		return result;
	}
	
	public static StateResult incomplete() {
		StateResult result = new StateResult();
		result.type = Type.INCOMPLETE;
		return result;
	}
}
