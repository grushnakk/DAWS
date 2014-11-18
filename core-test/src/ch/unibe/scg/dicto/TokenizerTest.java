package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import ch.unibe.scg.dicto.model.DictoAdapter;
import ch.unibe.scg.dicto.model.DictoConfiguration;
import ch.unibe.scg.dicto.model.DictoVariableDefinitionStatement;
import ch.unibe.scg.dicto.model.DictoVariableType;

public class TokenizerTest {

	Tokenizer tokenizer;
	
	@Before
	public void setUp() {
		//File Content
		DictoVariableType fileVariableType = new DictoVariableType("File");
		DictoAdapter fileContentAdapter = new DictoAdapter(fileVariableType);
		//Dependencies
		DictoVariableType packageVariableType = new DictoVariableType("Package");
		DictoVariableType parentClassVariableType = new DictoVariableType("Class");
		DictoAdapter dependenciesAdapter = new DictoAdapter(packageVariableType, parentClassVariableType);
		//Configuration
		DictoConfiguration configuration = new DictoConfiguration(new DictoVariableDefinitionStatement(), 
				fileContentAdapter, dependenciesAdapter);
		tokenizer = Tokenizer.createWithConfiguration(configuration);
	}
	
	@Test
	public void testFileContentVariableDefinition() {
		List<Token> actual = tokenizer.tokenize("BuildFile: File with path="); 
		List<Token> expected = new ArrayList<Token>(){{
			add(new Token("BuildFile", new TokenType("IDENTIFIER", null)));
			add(new Token(":", new TokenType("SYMBOL_COLON", null)));
			add(new Token("File", new TokenType("VARIABLE_TYPE(File)", null)));
			add(new Token("with", new TokenType("KEYWORD_WITH", null)));
			add(new Token("path", new TokenType("IDENTIFIER", null)));
			add(new Token("=", new TokenType("SYMBOL_ASSIGN", null)));
		}};
		assertThat(actual, equalTo(expected));
//		for(int i = 0; i < actual.size(); i++) {
//			assertThat(actual.get(i), equalTo(expected.get(i)));
//		}
	}
	
	@Test
	public void testDependenciesVariableDefinition1() {
		List<Token> actual = tokenizer.tokenize("View: Package with name="); 
		List<Token> expected = new ArrayList<Token>(){{
			add(new Token("View", new TokenType("IDENTIFIER", null)));
			add(new Token(":", new TokenType("SYMBOL_COLON", null)));
			add(new Token("Package", new TokenType("VARIABLE_TYPE(Package)", null)));
			add(new Token("with", new TokenType("KEYWORD_WITH", null)));
			add(new Token("name", new TokenType("IDENTIFIER", null)));
			add(new Token("=", new TokenType("SYMBOL_ASSIGN", null)));
		}};
		assertThat(actual, equalTo(expected));
//		for(int i = 0; i < actual.size(); i++) {
//			assertThat(actual.get(i), equalTo(expected.get(i)));
//		}
	}
	
	@Test
	public void testDependenciesVariableDefinition2() {
		List<Token> actual = tokenizer.tokenize("Model: Package with name="); 
		List<Token> expected = new ArrayList<Token>(){{
			add(new Token("Model", new TokenType("IDENTIFIER", null)));
			add(new Token(":", new TokenType("SYMBOL_COLON", null)));
			add(new Token("Package", new TokenType("VARIABLE_TYPE(Package)", null)));
			add(new Token("with", new TokenType("KEYWORD_WITH", null)));
			add(new Token("name", new TokenType("IDENTIFIER", null)));
			add(new Token("=", new TokenType("SYMBOL_ASSIGN", null)));
		}};
		assertThat(actual, equalTo(expected));
//		for(int i = 0; i < actual.size(); i++) {
//			assertThat(actual.get(i), equalTo(expected.get(i)));
//		}
	}
	
	@Test
	public void testDependenciesVariableDefinition3() {
		List<Token> actual = tokenizer.tokenize("Controller: Package with name="); 
		List<Token> expected = new ArrayList<Token>(){{
			add(new Token("Controller", new TokenType("IDENTIFIER", null)));
			add(new Token(":", new TokenType("SYMBOL_COLON", null)));
			add(new Token("Package", new TokenType("VARIABLE_TYPE(Package)", null)));
			add(new Token("with", new TokenType("KEYWORD_WITH", null)));
			add(new Token("name", new TokenType("IDENTIFIER", null)));
			add(new Token("=", new TokenType("SYMBOL_ASSIGN", null)));
		}};
		assertThat(actual, equalTo(expected));
//		for(int i = 0; i < actual.size(); i++) {
//			assertThat(actual.get(i), equalTo(expected.get(i)));
//		}
	}
	
	@Test
	public void testDependenciesVariableDefinition4() {
		List<Token> actual = tokenizer.tokenize("Tests: Class with parentClass="); 
		List<Token> expected = new ArrayList<Token>(){{
			add(new Token("Tests", new TokenType("IDENTIFIER", null)));
			add(new Token(":", new TokenType("SYMBOL_COLON", null)));
			add(new Token("Class", new TokenType("VARIABLE_TYPE(Package)", null)));
			add(new Token("with", new TokenType("KEYWORD_WITH", null)));
			add(new Token("parentClass", new TokenType("IDENTIFIER", null)));
			add(new Token("=", new TokenType("SYMBOL_ASSIGN", null)));
		}};
		assertThat(actual, equalTo(expected));
//		for(int i = 0; i < actual.size(); i++) {
//			assertThat(actual.get(i), equalTo(expected.get(i)));
//		}
	}
}
