package ch.unibe.scg.dicto.token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.petitparser.context.Context;
import org.petitparser.context.Result;
import org.petitparser.parser.ChoiceParser;
import org.petitparser.parser.CompositeParser;
import org.petitparser.parser.Parser;

import com.google.common.base.Function;

import ch.unibe.scg.dicto.VariableType;
import ch.unibe.scg.dicto.VariableTypeGrammar;

public class Tokenizer {
	protected final VariableTypeGrammar variableTypeGrammar;
	protected final List<VariableType> variableTypes;
	protected final Map<String, Parser> tokens;
	protected final CompositeParser internalTokenizer;
	

	public Tokenizer(VariableTypeGrammar variableTypeGrammar, List<VariableType> variableTypes) {
		this.variableTypeGrammar = variableTypeGrammar;
		this.variableTypes = variableTypes;
		this.tokens = new HashMap<>();
		this.internalTokenizer = new InternalTokenizer();
	}

	public List<Token> tokenize(String input) throws DictoParserException {
		Result result = internalTokenizer.parse(new Context(input));
		if(result.isSuccess())
			return result.<List<Token>>get();
		else
			throw new DictoParserException("unexcepted character");
	}
	
	public void defineToken(String name, Parser parser) {
		tokens.put(name, parser);
	}
	
	private class InternalTokenizer extends CompositeParser {
		
		@Override
		protected void initialize() {
			variableTypeGrammar.applyToTokenizer(Tokenizer.this, variableTypes);
			Parser choiceParser = new ChoiceParser();
			for(String name : tokens.keySet()) {
				defineToken(name, tokens.get(name));
				choiceParser = choiceParser.or(ref(name));
			}
			def("start", choiceParser.plus());
		}
		
		/*TODO this is getting ugly*/
		protected void defineToken(final String name, Parser parser) {
			def(name, parser.flatten().trim());
			action(name, new Function<String, Token>() {
				@Override
				public Token apply(final String input) {
					return new Token() {
						
						@Override
						public String getName() {
							return name;
						}
						
						@Override
						public String getContent() {
							return input;
						}
						
						@Override
						public void applyToTokenizer(Tokenizer tokenizer) {
						}
					};
				}
			});
		} 
	}
}
