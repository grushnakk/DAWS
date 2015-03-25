package ch.unibe.scg.dicto;

import ch.unibe.scg.dicto.parser.AcceptorResult;

public interface Path {
	
	public AcceptorResult accept(Context context);
	
	public void onSuccess(StateMachine stateMachine, AcceptorResult result);
}
