package ch.unibe.scg.dicto.states;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

import ch.unibe.scg.dicto.Token;

public class NewVariableTypeState extends AbstractState{

	static final Pattern tokenTypePattern = Pattern.compile("VARIABLE_TYPE\\((\\w+)\\)"); //FIXME magic value
	protected final Set<String> types;
	
	public NewVariableTypeState(State next, Set<String> types) {
		super(next);
		this.types = Collections.unmodifiableSet(types);
	}

	@Override
	protected boolean matches(Token token, StateMachine stateMachine) {
		return tokenTypePattern.matcher(token.getType().getName()).matches();
	}

	@Override
	protected State success(Token token, StateMachine stateMachine) {
		String type = token.getContent();
		if(!types.contains(type))
			return null;
		stateMachine.put("VAR_TYPE", type); //FIXME magic value
		return next;
	}

	@Override
	protected void failure(Token token, StateMachine stateMachine) {
		// TODO Auto-generated method stub
		
	}

}
