package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.Context;

public class RangeAcceptor extends Acceptor {

	private final String range;
	
	public RangeAcceptor(String range) {
		this.range = range;
	}
	
	@Override
	public int accept(Context context, int offset) {
		char c = context.charAt(offset);
		for(int i = 0; i < range.length(); i++) {
			if(range.charAt(i) == c)
				return 1;
		}
		return FAILURE;
	}

}
