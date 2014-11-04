package ch.unibe.scg.dicto.token;

import org.petitparser.Chars;
import org.petitparser.Parsers;
/*TODO: many magic numbers*/
public enum Tokens implements Token {

	IDENTIFIER("Identifier") {
		@Override
		public void applyToTokenizer(Tokenizer tokenizer) {
			tokenizer.defineToken("Identifier", Chars.letter().plus());
		}
	},
	EQUALS("SymbolEquals") {
		
		@Override
		public void applyToTokenizer(Tokenizer tokenizer) {
			tokenizer.defineToken("SymbolEquals", Chars.character('='));
		}
	},
	WITH("KeywordWith") {
		
		@Override
		public void applyToTokenizer(Tokenizer tokenizer) {
			tokenizer.defineToken("KeywordWith", Parsers.string("with"));
		}
	},
	COLON("SymbolColon") {
		
		@Override
		public void applyToTokenizer(Tokenizer tokenizer) {
			tokenizer.defineToken("SymbolColon", Chars.character(':'));
		}
	};
	
	private String name;
	
	Tokens(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getContent() {
		return "";
	}
}
