package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.parser.AcceptorResult.Type;

public class RangeAcceptor extends Acceptor {

	private final String range;
	
	public RangeAcceptor(String range) {
		this.range = range;
	}
	
	@Override
	public AcceptorResult accept(Context context, AcceptorResult result) {
		if(result.isFailure()) return result;
		if(result.end >= context.size()) {result.resultType = Type.INCOMPLETE; return result;}
		char c = context.charAt(result.end);
		if(isInRange(c)) {
			result.end++;
			return result;
		}
		result.resultType = Type.FAILURE;
		return result;
	}

	protected boolean isInRange(char c) {
		for(int i = 0; i < range.length(); i++) {
			if(range.charAt(i) == c) {
				return true;
			}
		}
		return false;
	}
}
