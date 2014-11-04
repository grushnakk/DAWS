package ch.unibe.scg.dicto.token;

import java.util.ArrayList;
import java.util.List;

import org.petitparser.parser.ChoiceParser;
import org.petitparser.parser.CompositeParser;
import org.petitparser.parser.Parser;

import com.google.common.base.Function;

import ch.unibe.scg.dicto.VariableType;
import ch.unibe.scg.dicto.VariableTypeGrammar;

public class Tokenizer extends CompositeParser {
	protected final VariableTypeGrammar variableTypeGrammar;
	protected final List<VariableType> variableTypes;
	protected final List<String> tokenNames;
	

	public Tokenizer(VariableTypeGrammar variableTypeGrammar, List<VariableType> variableTypes) {
		this.variableTypeGrammar = variableTypeGrammar;
		this.variableTypes = variableTypes;
		this.tokenNames = new ArrayList<>();
	}

	public List<Token> tokenize(String string) {
		throw new UnsupportedOperationException("not supported yet");
	}

	@Override
	protected void initialize() {
		variableTypeGrammar.applyToTokenizer(this, variableTypes);
		Parser choiceParser = new ChoiceParser();
		for(String name : tokenNames) {
			choiceParser = choiceParser.or(ref("tokenNames"));
		}
		def("start", choiceParser);
	}
	
	/*TODO this is getting ugly*/
	public void defineToken(final String name, Parser parser) {
		def(name, parser.flatten());
		action(name, new Function<String, Token>() {
			@Override
			public Token apply(String input) {
				return null; /*TODO yolo*/
			}
		})
		tokenNames.add(name);
	} 
}
