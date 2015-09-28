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

public class DictoAcceptanceDefStatementTests {
	
	StateMachine dicto;
	Environment env;

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
	public void newIdentifierIncomplete() {
		StateMachineResult StateMachineResult = result("View");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void newIdentifierComplete() {
		StateMachineResult StateMachineResult = result("View = ");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void newIdentifierComplete2() {
		StateMachineResult StateMachineResult = result("View =");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void newIdentifierComplete3() {
		StateMachineResult StateMachineResult = result("View=");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void notEnoughWhitespace() {
		StateMachineResult actual = result("View=Packagewith");
		assertFalse(actual.toString(), actual.isSuccess());
	}
	
	@Test
	public void typeIncomplete() {
		StateMachineResult StateMachineResult = result("View = Pack");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void typeIncomplete2() {
		StateMachineResult StateMachineResult = result("View =Pack");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void typeIncomplete3() {
		StateMachineResult StateMachineResult = result("View=Pack");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void typeComplete() {
		StateMachineResult StateMachineResult = result("View = Package");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void typeCompleteWithWhitespace() {
		StateMachineResult StateMachineResult = result("View = Package ");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void withComplete() {
		StateMachineResult StateMachineResult = result("View = Package with");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void withIncomplete() {
		StateMachineResult StateMachineResult = result("View = Package wi");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void argNameIncomplete() {
		StateMachineResult StateMachineResult = result("View = Package with na");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void argNameComplete() {
		StateMachineResult StateMachineResult = result("View = Package with name");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void argComplete() {
		StateMachineResult StateMachineResult = result("View = Package with name : \"org.app.View\"");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void argComplete1() {
		StateMachineResult StateMachineResult = result("View = Package with name:\"org.app.View\"");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void argComplete2() {
		StateMachineResult StateMachineResult = result("View = Package with name: \"org.app.View\"");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void argComplete3() {
		StateMachineResult StateMachineResult = result("View = Package with name :\"org.app.View\"");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void wrongArg() {
		StateMachineResult StateMachineResult = result("View = Package with stupid :");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSyntaxError());
	}
	
	@Test
	public void argIncomplete() {
		StateMachineResult StateMachineResult = result("View = Package with name : ");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());
	}
	
	@Test
	public void multiArgIncomplete() {
		StateMachineResult StateMachineResult = result("View = Package with name : \"bla\", n");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());	
	}
	
	@Test
	public void multiArgComplete() {
		StateMachineResult StateMachineResult = result("View = Package with name : \"bla\", name : \"and still bla\"");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());	
	}
	
	@Test
	public void twoStatements() {
		StateMachineResult StateMachineResult = result("View = Package with name : \"bla\""
				+ "\nView2 = Package wi");
		assertTrue(StateMachineResult.toString(), StateMachineResult.isSuccess());	
	}
}
