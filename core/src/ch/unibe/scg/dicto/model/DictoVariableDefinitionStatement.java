package ch.unibe.scg.dicto.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.petitparser.Chars;
import org.petitparser.Parsers;
import org.petitparser.parser.ChoiceParser;
import org.petitparser.parser.Parser;

import com.google.common.collect.ImmutableMap;

import ch.unibe.scg.dicto.TokenType;
import ch.unibe.scg.dicto.states.KeywordState;
import ch.unibe.scg.dicto.states.NewVariableIDState;
import ch.unibe.scg.dicto.states.NewVariableTypeState;
import ch.unibe.scg.dicto.states.State;

public class DictoVariableDefinitionStatement {
	
	 protected static final ImmutableMap<Character, Character> ESCAPE_TABLE =
		      ImmutableMap.<Character, Character>builder()
		          .put('\\', '\\')
		          .put('/', '/')
		          .put('"', '"')
		          .put('b', '\b')
		          .put('f', '\f')
		          .put('n', '\n')
		          .put('r', '\r')
		          .put('t', '\t')
		          .build();

	public Iterator<TokenType> tokenTypes(DictoVariableType type) {
		Set<TokenType> tokenTypes = new HashSet<>();
		//FIXME magic value
		tokenTypes.add(new TokenType("VARIABLE_TYPE(" + type.getName() + ")", Parsers.string(type.getName())));
		Iterator<DictoVariableArgument> arguments = type.arguments();
		while(arguments.hasNext()) {
			DictoVariableArgument argument = arguments.next();
			tokenTypes.add(new TokenType("ARGUMENT(" + argument.getName() +"@" + type.getName() + ")", Parsers.string(argument.getName())));
		}
		return tokenTypes.iterator();
	}

	public Iterator<TokenType> tokenTypes() {
		Set<TokenType> tokenTypes = new HashSet<>();
		tokenTypes.add(new TokenType("SYMBOL_COLON", Chars.character(':')));
		tokenTypes.add(new TokenType("SYMBOL_ASSIGN", Chars.character('=')));
		tokenTypes.add(new TokenType("KEYWORD_WITH", Parsers.string("with")));
		tokenTypes.add(new TokenType("IDENTIFIER", Chars.pattern("A-Za-z0-9").plus()));
		Parser characterEscape = Chars.character('\\').seq(Chars.anyOf(new String(com.google.common.primitives.Chars.toArray(ESCAPE_TABLE.keySet()))));
		Parser characterOctal = Parsers.string("\\u").seq(Chars.pattern("0-9A-Fa-f").times(4).flatten());
		Parser characterNormal = Chars.anyOf("\"\\").negate();
		Parser character = new ChoiceParser(characterEscape, characterOctal, characterNormal);
		tokenTypes.add(new TokenType("VALUE_STRING", Chars.character('"')
		        .seq(character.star())
		        .seq(Chars.character('"'))));
		return tokenTypes.iterator();
	}
	
	public State getVariableDefinitionState(DictoConfiguration config) {
		Set<String> types = extractTypes(config);
		State with = new KeywordState(new TokenType("KEYWORD_WITH"), null);
		State type = new NewVariableTypeState(with, types);
		State colon = new KeywordState(new TokenType("KEYWORD_COLOn"), type);
		State identifer = new NewVariableIDState(colon);
		return identifer;
	}
	
	protected Set<String> extractTypes(DictoConfiguration config) {
		HashSet<String> types = new HashSet<String>(); //FIXME treeset prolly more efficient
		Iterator<DictoAdapter> adapters = config.adapters();
		while(adapters.hasNext()) {
			Iterator<DictoVariableType> variableTypes = adapters.next().variableTypes();
			while(variableTypes.hasNext()) {
				types.add(variableTypes.next().getName());
			}
		}
		return types;
	}
	
}
