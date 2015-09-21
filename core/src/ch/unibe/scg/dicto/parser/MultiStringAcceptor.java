package ch.unibe.scg.dicto.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.unibe.scg.dicto.parser.AcceptorResult.Type;

public class MultiStringAcceptor extends Acceptor {

	private List<String> strings;
	
	public MultiStringAcceptor(String... strings) {
		this.strings = new ArrayList<>();
		for(String s : strings)
			this.strings.add(s);
	}
	
	public MultiStringAcceptor(List<String> strings) {
		this.strings = Collections.unmodifiableList(strings);
	}

	@Override
	public AcceptorResult accept(Context context, final AcceptorResult result) {
		if(result.resultType == Type.FAILURE) return result;
		int length;
		String actual;
		for(String match : strings) {
			length = Math.min(context.size() - result.end, match.length());
			actual = context.substring(result.end, result.end + length);
			if(match.startsWith(actual)) {
				result.end += length;
				if(length < match.length())
					result.resultType = Type.INCOMPLETE;
				return result;
			}
		}
		result.resultType = Type.FAILURE;
		return result;
	}
}
