package ch.unibe.scg.dicto.states2;

import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_DIGITS;
import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_LETTERS;
import static ch.unibe.scg.dicto.parser.Acceptors.string;
import static ch.unibe.scg.dicto.parser.Acceptors.range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Predicate;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.Context;

public final class Example {

	public static void main(String[] args) {
		Acceptor idAcceptor = range(RANGE_DIGITS + RANGE_LETTERS).repeat();
		StateMachineBuilder sMachine = new StateMachineBuilder();
		sMachine.state("STATE_START")
				.pathTo("PATH_UNKNOWN_ID")
				.accepts(idAcceptor)
				.suggestsNothing()
				.complete();
		sMachine.state("STATE_START")
				.pathTo("PATH_ONLY")
				.accepts(string("only"))
				.suggests("only")
				.complete();
		StateMachine stateMachine = sMachine.build();
		stateMachine.run(new Context("only"), buildEnv());
	}

	/*
	 * IGNORE THIS
	 */
	@SuppressWarnings("serial")
	static Environment buildEnv() {
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
		return new Environment(variables, types);
	}
}
