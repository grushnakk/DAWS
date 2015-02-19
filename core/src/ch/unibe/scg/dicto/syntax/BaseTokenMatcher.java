package ch.unibe.scg.dicto.syntax;

import org.petitparser.context.Context;
import org.petitparser.context.Result;
import org.petitparser.parser.Parser;

public class BaseTokenMatcher implements TokenMatcher {

	private final TokenType type;
	private final Parser parser;
	private final TokenMatcher next;
	
	public BaseTokenMatcher(TokenType type, Parser parser, TokenMatcher next) {
		this.type = type;
		this.parser = parser;
		this.next = next;
	}
	
	//TODO eliminate whitespace
	/* (non-Javadoc)
	 * @see ch.unibe.scg.dicto.syntax.TokenMatcher#match(ch.unibe.scg.dicto.syntax.DictoContext, int)
	 */
	@Override
	public Token match(DictoContext context, int index) {
		Context parserContext = new Context(new String(new StringBuilder(context.sequence(index))));
		Result result = parser.parse(parserContext);
		if(result.isFailure())
			return null;
		return new Token(index, result.getPosition(), context, type);
	}

	@Override
	public TokenMatcher next(TokenType type) {
		return next;
	}
	
	@Override
	public TokenType getType() {
		return type;
	}
}
