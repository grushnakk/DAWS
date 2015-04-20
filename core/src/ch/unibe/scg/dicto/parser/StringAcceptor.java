package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.parser.AcceptorResult.State;

public class StringAcceptor extends Acceptor {

	private final String match;
	
	public StringAcceptor(String match) {
		this.match = match;
	}
	
	@Override
	public AcceptorResult accept(Context context, final AcceptorResult result) {
		if(result.state == State.FAILURE) return result;
		int length = Math.min(context.size() - result.end, match.length());
		String actual = context.substring(result.end, result.end + length);
		if(match.startsWith(actual)) {
			result.end += length;
			if(length < match.length())
				result.state = State.INCOMPLETE;
			return result;
		}
		result.state = State.FAILURE;
		return result;
	}
}
