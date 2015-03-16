package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.Context;

public class OptionalAcceptor extends Acceptor{

	private final Acceptor baseAcceptor;
	
	public OptionalAcceptor(Acceptor baseAcceptor) {
		this.baseAcceptor = baseAcceptor;
	}
	
	@Override
	public int accept(Context context, int offset) {
		int result = baseAcceptor.accept(context, offset);
		if(result == FAILURE)
			return 0;
		return result;
	}

}
