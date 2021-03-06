package ch.unibe.scg.dicto.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EnvironmentTests {

	Environment env;
	
	@Before
	public void setUp() {
		List<VariableType> types = new ArrayList<>();
		List<Argument> arguments = new ArrayList<>();
		types.add(new VariableType("Package", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("Class", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("Website", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("File", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("Component", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("XMLTag", arguments, new ArrayList<Rule>()));
		types.add(new VariableType("Attribute", arguments, new ArrayList<Rule>()));
		List<Variable> variables = new ArrayList<>();
		variables.add(new Variable("View", types.get(0), new HashMap<String, String>()));
		env = new Environment(variables, types);
	}

	@Test
	public void variableTypeUndefined() {
		assertFalse(env.isTypeDefined("Garbage"));
	}
	
	@Test
	public void variableTypeDefined() {
		assertTrue(env.isTypeDefined("Package"));
	}
	
	@Test 
	public void variableDefined() {
		assertTrue(env.isVariableDefined("View"));
	}
}
