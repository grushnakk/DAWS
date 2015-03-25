package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.Context;
import ch.unibe.scg.dicto.parser.AcceptorResult.State;

public class OptionalAcceptor extends Acceptor{

	private final Acceptor baseAcceptor;
	
	public OptionalAcceptor(Acceptor baseAcceptor) {
		this.baseAcceptor = baseAcceptor;
	}
	
	@Override
	public AcceptorResult accept(Context context, AcceptorResult result) {
		if(result.state == State.FAILURE) return result;
		baseAcceptor.accept(context, result);
		if(result.state == State.FAILURE) {
			result.state = State.SUCCESS;
			return result;
		}
		return result;
	}

	@Override
	public Acceptor optional() {
		return this;
	}
}
