package ch.unibe.scg.dicto.syntax;

import org.petitparser.Chars;
import org.petitparser.parser.Parser;

public class Tokenizer {

	public static final TokenMatcher DICTO_MATCHER;
	
	static {
		Parser idParser = Chars.pattern("[A-Za-z][A-Za-z0-9_]*");
		DICTO_MATCHER = new BaseTokenMatcher(TokenType.IDENTIFIER, idParser, null); //TODO replace null
		
	}
	
	
	public Tokenizer() {
		
	}
	
	public void parse(DictoContext context) {
		int index = 0;
		TokenMatcher currentMatcher = DICTO_MATCHER;
		while(index < context.length() - 1) {
			currentMatcher.match(context, index);
		}
	}
}
