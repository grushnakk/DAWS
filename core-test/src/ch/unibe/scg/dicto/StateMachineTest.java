package ch.unibe.scg.dicto;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.model.DictoAdapter;
import ch.unibe.scg.dicto.model.DictoConfiguration;
import ch.unibe.scg.dicto.model.DictoVariableArgument;
import ch.unibe.scg.dicto.model.DictoVariableDefinitionStatement;
import ch.unibe.scg.dicto.model.DictoVariableType;
import ch.unibe.scg.dicto.states.State;
import ch.unibe.scg.dicto.states.StateMachine;
import ch.unibe.scg.dicto.states.UnexpectedTokenException;

public class StateMachineTest {

	State first;
	
	@Before
	public void setUp() {
		//File Content
		DictoVariableArgument path = new DictoVariableArgument("path", false, false);
		DictoVariableType fileVariableType = new DictoVariableType("File", path);
		DictoAdapter fileContentAdapter = new DictoAdapter(fileVariableType);
		//Dependencies
		DictoVariableArgument name = new DictoVariableArgument("name", false, false);
		DictoVariableType packageVariableType = new DictoVariableType("Package", name);
		DictoVariableArgument parentClass = new DictoVariableArgument("parentClass", false, false);
		DictoVariableType parentClassVariableType = new DictoVariableType("Class", parentClass);
		DictoAdapter dependenciesAdapter = new DictoAdapter(packageVariableType, parentClassVariableType);
		//Configuration
		DictoConfiguration configuration = new DictoConfiguration(new DictoVariableDefinitionStatement(), 
				fileContentAdapter, dependenciesAdapter);
		first = configuration.getVariableDefinitionStatement().getVariableDefinitionState(configuration);
	}
	
	@Test
	public void testsClassWithParentClass() throws UnexpectedTokenException {
		StateMachine machine = new StateMachine(first);
		List<Token> input = new ArrayList<Token>() {{
			add(new Token("Tests", new TokenType("IDENTIFIER")));
			add(new Token(":", new TokenType("SYMBOL_COLON")));
			add(new Token("Class", new TokenType("VARIABLE_TYPE(Class)")));
			add(new Token("with", new TokenType("KEYWORD_WITH")));
			add(new Token("parentClass", new TokenType("ARGUMENT(parentClass@Class)")));
			add(new Token("=", new TokenType("SYMBOL_ASSIGN")));
		}};
		machine.consume(input);
		String expectedVarType = (String)machine.get("VAR_TYPE");
		String expectedVarName = (String)machine.get("VAR_NAME");
		assertEquals(expectedVarType, "Class");
		assertEquals(expectedVarName, "Tests");
	}
}
