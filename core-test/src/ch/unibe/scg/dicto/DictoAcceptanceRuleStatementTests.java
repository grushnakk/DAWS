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

public class DictoAcceptanceRuleStatementTests {


	StateMachine dicto;
	Environment env;

	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		List<VariableType> types = new ArrayList<>();
		List<Argument> arguments = new ArrayList<>();
		List<Argument> packageArguments = new ArrayList<>();
		List<Rule> packageRules = new ArrayList<>();
		packageRules.add(new Rule("depend on", new ArrayList<Predicate>(){{add(Predicate.MUST);add(Predicate.CANNOT);}}));
		packageArguments.add(new Argument("name"));
		types.add(new VariableType("Package", packageArguments, packageRules));
		types.add(new VariableType("Class", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("Website", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("File", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("Component", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("XMLTag", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("Attribute", arguments, new ArrayList<Rule>()));
		env = new Environment(new ArrayList<Variable>(), types);
		env.addVariable(new Variable("View", env.getVariableType("Package"), new HashMap<String, String>()));
		env.addVariable(new Variable("Controller", env.getVariableType("Package"), new HashMap<String, String>()));
		dicto = new Dicto().build(env);
	}
	
	public StateMachineResult result(String content) {
		return dicto.run(new Context(content), env);
	}
	
	@Test
	public void viewIncomplete() {
		StateMachineResult actual = result("Vie");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void viewComplete() {
		StateMachineResult actual = result("View ");
		assertTrue(actual.toString(), actual.isSuccess());
	}

	@Test
	public void predicateIncomplete() {
		StateMachineResult actual = result("View cann");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void predicateComplete() {
		StateMachineResult actual = result("View cannot");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void ruleIncomplete() {
		StateMachineResult actual = result("View must dep");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void ruleIncomplete2() {
		StateMachineResult actual = result("View must depend o");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void ruleComplete() {
		StateMachineResult actual = result("View must depend on");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void ruleCompleteWithWhitespace() {
		StateMachineResult actual = result("View must depend on ");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void statementComplete() {
		StateMachineResult actual = result("View must depend on Controller");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void statementComplete2() {
		StateMachineResult actual = result("View can depend on Controller");
		assertTrue(actual.toString(), actual.isSyntaxError());
	}
}
