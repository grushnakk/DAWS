package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.parser.AcceptorResult.Type;

public class RepeatAcceptor extends Acceptor {

	private final Acceptor baseAcceptor;
	
	public RepeatAcceptor(Acceptor baseAcceptor) {
		this.baseAcceptor = baseAcceptor;
	}
	
	@Override
	public AcceptorResult accept(Context context, final AcceptorResult result) {
		if(!result.isSuccess()) return result;
		AcceptorResult copy = null;
		AcceptorResult save = new AcceptorResult(result);
		boolean accOnce = false;
		do {
			copy = new AcceptorResult(save);
			baseAcceptor.accept(context, copy);
			if(!copy.isFailure()) {
				save = copy;
				accOnce = true;
			}
		} while(save.end < context.size() && copy.isSuccess());
		result.set(save);
		if(result.isFailure() && accOnce)
			result.resultType = Type.SUCCESS;
		return result;
	}
	
	@Override
	public Acceptor repeat() {
		return this;
	}
}
