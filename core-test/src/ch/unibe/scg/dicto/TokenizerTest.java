package ch.unibe.scg.dicto;

import org.junit.Before;

import ch.unibe.scg.dicto.token.Tokenizer;
import ch.unibe.scg.dicto.token.Tokens;

public class TokenizerTest {

	protected Tokenizer tokenizer;
	
	@Before
	public void setUp() {
		Configuration configuration = Configuration.getInstance();
		configuration.addVariableType(new VariableType("Package", new Argument("name", false, false)));
		configuration.addVariableType(new VariableType("Class", new Argument("parentClass", false, false)));
		configuration.setVariableTypeGrammar(new VariableTypeGrammar(Tokens.IDENTIFIER));
		configuration.close();
		tokenizer = new Tokenizer(configuration.getVariableTypeGrammar(), configuration.getVariableTypes());
	}
}
