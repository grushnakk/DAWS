package ch.unibe.scg.dicto.parser;


import ch.unibe.scg.dicto.parser.AcceptorResult.State;

public class RepeatAcceptor extends Acceptor {

	private final Acceptor baseAcceptor;
	
	public RepeatAcceptor(Acceptor baseAcceptor) {
		this.baseAcceptor = baseAcceptor;
	}
	
	@Override
	public AcceptorResult accept(Context context, final AcceptorResult result) {
		if(result.isFailure()) return result;
		AcceptorResult copy = null;
		AcceptorResult save = new AcceptorResult(result);
		boolean accOnce = false; //XXX is this ugly
		do {
			copy = new AcceptorResult(save);
			baseAcceptor.accept(context, copy);
			if(!copy.isFailure()) {
				save = copy;
				accOnce = true;
			}
		} while(!copy.isFailure() && context.size() > save.end);
		result.set(save);
		if(!accOnce)
			result.state = State.FAILURE;
		return result;
	}
	
	@Override
	public Acceptor repeat() {
		return this;
	}
}
