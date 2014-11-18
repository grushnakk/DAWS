package ch.unibe.scg.dicto.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.petitparser.Chars;
import org.petitparser.Parsers;

import ch.unibe.scg.dicto.TokenType;

public class DictoVariableDefinitionStatement {

	public Iterator<TokenType> tokenTypes(DictoVariableType type) {
		Set<TokenType> tokenTypes = new HashSet<>();
		//TODO magic value
		tokenTypes.add(new TokenType("VARIABLE_TYPE(" + type.getName() + ")", Parsers.string(type.getName())));
		return tokenTypes.iterator();
	}

	public Iterator<TokenType> tokenTypes() {
		Set<TokenType> tokenTypes = new HashSet<>();
		tokenTypes.add(new TokenType("SYMBOL_COLON", Chars.character(':')));
		tokenTypes.add(new TokenType("SYMBOL_ASSIGN", Chars.character('=')));
		tokenTypes.add(new TokenType("KEYWORD_WITH", Parsers.string("with")));
		tokenTypes.add(new TokenType("IDENTIFIER", Chars.pattern("A-Za-z0-9").plus()));
		return tokenTypes.iterator();
	}
	
}