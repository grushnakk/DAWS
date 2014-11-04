package ch.unibe.scg.dicto.token;

import org.petitparser.Chars;

public enum Tokens implements Token {

	IDENTIFIER {
		@Override
		public void applyToTokenizer(Tokenizer tokenizer) {
			tokenizer.defineToken("IDENTIFIER", Chars.letter().plus());
		}
	};
}
