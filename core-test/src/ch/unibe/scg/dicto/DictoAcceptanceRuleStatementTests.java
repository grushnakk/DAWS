package ch.unibe.scg.dicto;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Predicate;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Context;
import ch.unibe.scg.dicto.states.StateMachine;
import ch.unibe.scg.dicto.states.StateResult;

public class DictoAcceptanceRuleStatementTests {


	@SuppressWarnings("serial")
	public static StateResult setUp(String context) {
		List<VariableType> types = new ArrayList<>();
		List<Argument> packageArguments = new ArrayList<>();
		packageArguments.add(new Argument("name"));
		types.add(new VariableType("Package", packageArguments, new ArrayList<Rule>()));
		List<Variable> variables = new ArrayList<>();
		variables.add(new Variable("View", types.get(0), new HashMap<String, String>(){

			private static final long serialVersionUID = 1L;

		{put("name", "org.app.view");}}));
		List<Rule> rules = new ArrayList<>();
		rules.add(new Rule("depend on", new ArrayList<String>(){{add("must");}}));
		Environment env = new Environment(variables, types);
		StateMachine dicto = new DictoBuilder(env).build().clone(env, new Context(context));
		dicto.run();
		return dicto.getResult();
	}
	
	@Test
	public void viewIncomplete() {
		StateResult actual = setUp("Vie");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void viewComplete() {
		StateResult actual = setUp("View ");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void predicateIncomplete() {
		StateResult actual = setUp("View cann");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void predicateComplete() {
		StateResult actual = setUp("View cannot");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void ruleIncomplete() {
		StateResult actual = setUp("View must dep");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void ruleIncomplete2() {
		StateResult actual = setUp("View must depend o");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void ruleComplete() {
		StateResult actual = setUp("View must depend on");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void ruleCompleteWithWhitespace() {
		StateResult actual = setUp("View must depend on ");
		assertTrue(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void statementComplete() {
		StateResult actual = setUp("View must depend on Controller");
		assertTrue(actual.toString(), actual.isSuccess());
	}
}
