package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.parser.AcceptorResult.Type;

public class OptionalAcceptor extends Acceptor{

	private final Acceptor baseAcceptor;
	
	public OptionalAcceptor(Acceptor baseAcceptor) {
		this.baseAcceptor = baseAcceptor;
	}
	
	@Override
	public AcceptorResult accept(Context context, AcceptorResult result) {
		if(result.resultType == Type.FAILURE) return result;
		baseAcceptor.accept(context, result);
		if(!result.isSuccess()) {
			result.resultType = Type.SUCCESS;
			return result;
		}
		return result;
	}

	@Override
	public Acceptor optional() {
		return this;
	}
}
