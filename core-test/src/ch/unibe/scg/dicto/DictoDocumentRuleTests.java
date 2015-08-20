package ch.unibe.scg.dicto;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Context;
import ch.unibe.scg.dicto.states.StateMachine;
import ch.unibe.scg.dicto.states.StateResult;

public class DictoDocumentRuleTests {

	Environment env;
	
	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		/*
		 * PACKAGE_TYPE
		 */
		List<Argument> packageArgs = new ArrayList<>();
		packageArgs.add(new Argument("name"));
		List<Rule> packageRules = new ArrayList<>();
		packageRules.add(new Rule())
		VariableType packageType = new VariableType("Package", packageArgs, packageRules);
		/*
		 * PACKAGE_VARIABLES
		 */
		Variable view = new Variable("View", packageType, new HashMap<String, String>(){{put("name", "org.app.view");}});
		Variable model = new Variable("Model", packageType, new HashMap<String, String>(){{put("name", "org.app.model");}});
		Variable controller = new Variable("Controller", packageType, new HashMap<String, String>(){{put("name", "org.app.controller");}});
		Variable tests = new Variable("Tests", packageType, new HashMap<String, String>(){{put("name", "junit.framework.TestCase");}});
		
		List<VariableType> types = new ArrayList<>();
		types.add(packageType);
		List<Variable> variables = new ArrayList<>();
		variables.add(view);
		variables.add(model);
		variables.add(controller);
		variables.add(tests);
		env = new Environment(variables, types);
	}
	
	@Test
	public void dependencies1() {
		StateResult actual = computeResult("Controller must depend on Model");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	StateResult computeResult(String in) {
		StateMachine dicto = new DictoBuilder(env).build().clone(env, new Context(in));
		dicto.run();
		return dicto.getResult();
	}
}
