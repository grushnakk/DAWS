package ch.unibe.scg.dicto.parser;


import ch.unibe.scg.dicto.Context;

public class RepeatAcceptor extends Acceptor {

	private final Acceptor baseAcceptor;
	
	public RepeatAcceptor(Acceptor baseAcceptor) {
		this.baseAcceptor = baseAcceptor;
	}
	
	@Override
	public int accept(Context context, int offset) {
		int length = 0;
		int result = FAILURE;
		while(context.size() > 0 && (result = baseAcceptor.accept(context, length)) != FAILURE)
			length += result;
		return length;
	}
}
