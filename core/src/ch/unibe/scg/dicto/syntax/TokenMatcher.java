package ch.unibe.scg.dicto.syntax;

public interface TokenMatcher {
	
	//TODO eliminate whitespace
	public Token match(DictoContext context, int index);
	
	public TokenMatcher next(TokenType type);
	
	public TokenType getType();
}