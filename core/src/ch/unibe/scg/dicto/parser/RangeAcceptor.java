package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.Context;
import ch.unibe.scg.dicto.parser.Result.State;

public class RangeAcceptor extends Acceptor {

	private final String range;
	
	public RangeAcceptor(String range) {
		this.range = range;
	}
	
	@Override
	public Result accept(Context context, Result result) {
		char c = context.charAt(result.end);
		for(int i = 0; i < range.length(); i++) {
			if(range.charAt(i) == c) {
				result.end++;
				return result;
			}
		}
		result.state = State.FAILURE;
		return result;
	}

}
