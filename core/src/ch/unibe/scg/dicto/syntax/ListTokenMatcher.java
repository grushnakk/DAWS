package ch.unibe.scg.dicto.syntax;

public class ListTokenMatcher implements TokenMatcher {

	private TokenMatcher[] matchers;
	
	public ListTokenMatcher(TokenMatcher... matchers) {
		this.matchers = matchers;
	}
	
	@Override
	public Token match(DictoContext context, int index) {
		Token token = null;
		for(TokenMatcher matcher : matchers) {
			token = matcher.match(context, index);
			if(token != null)
				return token;
		}
		return null;
	}
	
	@Override
	public TokenMatcher next(TokenType type) {
		for(TokenMatcher matcher : matchers) {
			if(matcher.getType() == type)
				return matcher.next(type);
		}
		return null;
	}
	
	@Override
	public TokenType getType() {
		return null; //TODO WHAT TO DO HERE
	}
}
