package ch.unibe.scg.dicto;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.unibe.scg.dicto.token.DictoParserException;
import ch.unibe.scg.dicto.token.Token;
import ch.unibe.scg.dicto.token.Tokenizer;
import ch.unibe.scg.dicto.token.Tokens;

public class TokenizerTest {

	protected static Tokenizer tokenizer;
	
	@BeforeClass
	public static void setUp() {
		Configuration configuration = Configuration.getInstance();
		configuration.addVariableType(new VariableType("Package", new Argument("name", false, false)));
		configuration.addVariableType(new VariableType("Class", new Argument("parentClass", false, false)));
		configuration.setVariableTypeGrammar(new VariableTypeGrammar(Tokens.IDENTIFIER));
		configuration.close();
		tokenizer = new Tokenizer(configuration.getVariableTypeGrammar(), configuration.getVariableTypes());
	}
	
	@Test
	public void testIdentifier() throws DictoParserException {
		List<Token> result = tokenizer.tokenize("IdentifierA");
		assertEquals(1, result.size());
		assertEquals("IdentifierA", result.get(0).getContent());
	}
	
	@Test
	public void testTwoIdentifier() throws DictoParserException {
		List<Token> result = tokenizer.tokenize("IdentifierA IdentifierB");
		assertEquals(2, result.size());
		assertEquals("IdentifierA", result.get(0).getContent());
		assertEquals("IdentifierB", result.get(1).getContent());
	}
	
	@Test
	public void testDependenciesLine1() throws DictoParserException {
		List<Token> result = tokenizer.tokenize("View: Package with name=\"org.app.view\"");
		List<Token> expected = new ArrayList<>(
				
		);
	}
}
