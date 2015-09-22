package ch.unibe.scg.dicto;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Predicate;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Context;
import ch.unibe.scg.dicto.states2.StateMachine;
import ch.unibe.scg.dicto.states2.StateMachineResult;

public class DictoDocumentRuleTests {

	Environment env;
	StateMachine dicto;
	
	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		/*
		 * PACKAGE
		 */
		List<Argument> packageArgs = new ArrayList<>();
		packageArgs.add(new Argument("name"));
		List<Rule> packageRules = new ArrayList<>();
		packageRules.add(new Rule("depend on", new ArrayList<Predicate>(){{
			add(Predicate.MUST);
			add(Predicate.CANNOT);
			add(Predicate.CAN_ONLY);
		}}));
		packageRules.add(new Rule("access", new ArrayList<Predicate>() {{
			add(Predicate.ONLY_CAN);
		}}));
		VariableType pack = new VariableType("Package", packageArgs, packageRules);
		/*
		 * PACKAGE VARIABLES
		 */
		Variable view = new Variable("View", pack, new HashMap<String, String>(){{put("name", "org.app.view");}});
		Variable model = new Variable("Model", pack, new HashMap<String, String>(){{put("name", "org.app.model");}});
		Variable controller = new Variable("Controller", pack, new HashMap<String, String>(){{put("name", "org.app.controller");}});
		Variable tests = new Variable("Tests", pack, new HashMap<String, String>(){{put("name", "junit.framework.TestCase");}});
		/*
		 * WEBSITE
		 */
		List<Argument> websiteArgs = new ArrayList<>();
		websiteArgs.add(new Argument("url"));
		List<Rule> websiteRules = new ArrayList<>();
		websiteRules.add(new Rule("have latency <", new ArrayList<Predicate>(){{
			add(Predicate.MUST);
			add(Predicate.ONLY_CAN);
		}}));
		VariableType website = new VariableType("Website", websiteArgs, websiteRules);
		/*
		 * WEBSITE VARIABLES
		 */
		Variable google = new Variable("Google", website, new HashMap<String, String>(){{put("url", "http://www.google.com");}});
		Variable yahoo = new Variable("Yahoo", website, new HashMap<String, String>() {{put("url", "http://www.yahoo.com");}});
		/*
		 * FILE
		 */
		List<Argument> fileArgs = new ArrayList<>();
		fileArgs.add(new Argument("path"));
		List<Rule> fileRules = new ArrayList<>();
		fileRules.add(new Rule("contain text", new ArrayList<Predicate>(){{
			add(Predicate.MUST);
			add(Predicate.CANNOT);
			add(Predicate.CAN_ONLY);
			add(Predicate.ONLY_CAN);
		}}));
		VariableType file = new VariableType("File", fileArgs, fileRules);
		/*
		 * FILE VARIABLES
		 */
		Variable buildFile = new Variable("BuildFile", file, new HashMap<String, String>(){{put("path", "*/build/BUILD.XML");}});
		
		List<VariableType> types = new ArrayList<>();
		types.add(pack);
		types.add(website);
		types.add(file);
		List<Variable> variables = new ArrayList<>();
		variables.add(view);
		variables.add(model);
		variables.add(controller);
		variables.add(tests);
		variables.add(google);
		variables.add(yahoo);
		variables.add(buildFile);
		env = new Environment(variables, types);
		dicto = new Dicto().build(env);
	}
	
	StateMachineResult computeResult(String context) {
		return dicto.run(new Context(context), env);
	}
	
	@Test
	public void dependencies1() {
		StateMachineResult actual = computeResult("Controller must depend on Model");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void dependencies2() {
		StateMachineResult actual = computeResult("Model cannot depend on View, Controller");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void dependencies3() {
		StateMachineResult actual = computeResult("only Tests can access Model");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void dependencies4() {
		StateMachineResult actual = computeResult("Tests, Model can only depend on Controller");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void latency1() {
		StateMachineResult actual = computeResult("Google must have latency < \"1 ms\"");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void latency3() {
		StateMachineResult actual = computeResult("only Yahoo can have latency < \"1000 ms\"");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void fileContent1() {
		StateMachineResult actual = computeResult("BuildFile must contain text \"foobarbaz\"");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void fileContent2() {
		StateMachineResult actual = computeResult("BuildFile cannot contain text \"foobarbaz\"");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void fileContent3() {
		StateMachineResult actual = computeResult("BuildFile can only contain text \"foobarbaz\"");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void fileContent4() {
		StateMachineResult actual = computeResult("only BuildFile can contain text \"foobarbaz\"");
		assertTrue(actual.toString(), actual.isSuccess());
	}
}
