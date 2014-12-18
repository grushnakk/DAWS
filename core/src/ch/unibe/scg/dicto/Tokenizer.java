package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.petitparser.context.Context;
import org.petitparser.context.Result;
import org.petitparser.parser.ChoiceParser;
import org.petitparser.parser.CompositeParser;
import org.petitparser.parser.Parser;

import com.google.common.base.Function;

import ch.unibe.scg.dicto.model.DictoAdapter;
import ch.unibe.scg.dicto.model.DictoConfiguration;
import ch.unibe.scg.dicto.model.DictoVariableDefinitionStatement;
import ch.unibe.scg.dicto.model.DictoVariableType;

public class Tokenizer extends CompositeParser {

	private static DictoConfiguration configuration;
	private static List<String> tokenNames;
	
	public static Tokenizer createWithConfiguration(DictoConfiguration dictoConfiguration) {
		tokenNames = new ArrayList<>();
		configuration = dictoConfiguration;
		Tokenizer tokenizer = new Tokenizer();
		configuration = null;
		tokenNames = null;
		return tokenizer;
	}
	
	private Tokenizer() {
		super();
	}
	
	@Override
	protected void initialize() {	
		initializeDefinitions();
		initializeStart();
	}
	
	protected void initializeStart() {
		Parser[] tokenParsers = new Parser[tokenNames.size()];
		for(int i = 0; i < tokenParsers.length; i++) {
			tokenParsers[i] = ref(tokenNames.get(i));
		}
		//this is a petitparser design decision
		def("start", new ChoiceParser(tokenParsers).star());
	}
	
	protected void initializeDefinitions() {
		Iterator<DictoAdapter> adapters = Tokenizer.configuration.adapters();
		while(adapters.hasNext()) {
			Iterator<DictoVariableType> variableTypes = adapters.next().variableTypes();
			while(variableTypes.hasNext()) {
				initializeVariableType(variableTypes.next());
			}
		}
		initializeDefinitionStatement(Tokenizer.configuration.getVariableDefinitionStatement());
	}
	
	protected void initializeDefinitionStatement(
			DictoVariableDefinitionStatement variableDefinitionStatement) {
		Iterator<TokenType> tokenTypes = variableDefinitionStatement.tokenTypes();
		while(tokenTypes.hasNext()) {
			initializeTokenType(tokenTypes.next());
		}
	}

	protected void initializeVariableType(DictoVariableType type) {
		Iterator<TokenType> tokenTypes = configuration.getVariableDefinitionStatement().tokenTypes(type);
		while(tokenTypes.hasNext()) {
			initializeTokenType(tokenTypes.next());
		}
	}
	
	public List<Token> tokenize(String input) {
		Result result = parse(new Context(input));
		if(result.isFailure())
			throw new TokenizerException("something went wrong"); //FIXME the exception message :/
		return result.<List<Token>>get();
	}

	protected void initializeTokenType(final TokenType tokenType) {
		def(tokenType.getName(), tokenType.getParser().flatten().trim());
		action(tokenType.getName(), new Function<String, Token>() {

			@Override
			public Token apply(String input) {
				return new Token(input, tokenType);
			}
		});
		tokenNames.add(tokenType.getName());
	}
}
