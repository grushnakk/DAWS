package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.Context;

public class StringAcceptor extends Acceptor {

	private final String match;
	
	public StringAcceptor(String match) {
		this.match = match;
	}
	
	@Override
	public int accept(Context context, int offset) {
		int length = Math.min(context.sizeLeft() - offset, match.length());
		String actual = context.substring(length + offset);
		if(match.startsWith(actual))
			return length;
		return FAILURE;
	}
}
