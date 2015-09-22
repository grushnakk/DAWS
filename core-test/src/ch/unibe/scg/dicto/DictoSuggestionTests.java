package ch.unibe.scg.dicto;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Context;
import ch.unibe.scg.dicto.states2.StateMachine;
import ch.unibe.scg.dicto.states2.StateMachineResult;

public class DictoSuggestionTests {
	
	Environment env;
	StateMachine dicto;

	@Before
	public void setUp() {
		List<VariableType> types = new ArrayList<>();
		List<Argument> arguments = new ArrayList<>();
		List<Argument> packageArguments = new ArrayList<>();
		packageArguments.add(new Argument("name"));
		types.add(new VariableType("Package", packageArguments, new ArrayList<Rule>()));
		types.add(new VariableType("Class", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("Website", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("File", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("Component", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("XMLTag", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("Attribute", arguments, new ArrayList<Rule>()));
		env = new Environment(new ArrayList<Variable>(), types);
		dicto = new Dicto().build(env);
	}
	
	public StateMachineResult result(String content) {
		return dicto.run(new Context(content), env);
	}
	
	@Test
	public void newID() {
		StateMachineResult result = result("Vie");
		List<String> actual = result.getSuggestions();
		List<String> expected = list("only");
		assertEquals(expected, actual);
	}
	
	@Test
	public void type() {
		StateMachineResult result = result("View = ");
		List<String> actual = result.getSuggestions();
		List<String> expected = list("Package", "Class", "Website", "File", "Component", "XMLTag", "Attribute");
		assertEquals(expected, actual);
	}
	@Test
	public void with() {
		StateMachineResult result = result("View = Package ");
		List<String> actual = result.getSuggestions();
		List<String> expected = list("with");
		assertEquals(expected, actual);
	}
	
	@Test
	public void argName() {
		StateMachineResult result = result("View = Package with ");
		List<String> actual = result.getSuggestions();
		List<String> expected = list("name");
		assertEquals(expected, actual);
	}
	
	public List<String> list(String... args) {
		List<String> list = new ArrayList<>();
		for(String arg : args) {
			list.add(arg);
		}
		return list;
	}
}
