package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.token.Token;
import ch.unibe.scg.dicto.token.Tokenizer;

public class VariableTypeGrammar {
	
	protected final List<Token> staticTokens;
	
	public VariableTypeGrammar(Token... staticTokens) {
		this.staticTokens = new ArrayList<>();
		for(Token token: staticTokens) {
			this.staticTokens.add(token);
		}
	}

	public void applyToTokenizer(Tokenizer tokenizer, List<VariableType> variableTypes) {
		for(Token token: staticTokens) {
			token.applyToTokenizer(tokenizer);
		}
	}
}
