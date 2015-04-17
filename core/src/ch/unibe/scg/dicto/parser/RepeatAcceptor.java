package ch.unibe.scg.dicto.parser;

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
		do {
			copy = new AcceptorResult(save);
			baseAcceptor.accept(context, copy);
			if(!copy.isFailure()) {
				save = copy;
			}
		} while(!copy.isFailure());
		result.set(save);
		return result;
	}
	
	@Override
	public Acceptor repeat() {
		return this;
	}
}
