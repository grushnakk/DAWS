package ch.unibe.scg.dicto;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Context;
import ch.unibe.scg.dicto.states.StateMachine;
import ch.unibe.scg.dicto.states.StateResult;

public class DictoAcceptanceDefStatementTests {
	
	public static StateResult setUp(String context) {
		List<VariableType> types = new ArrayList<>();
		List<Argument> arguments = new ArrayList<>();
		List<Argument> packageArguments = new ArrayList<>();
		List<Rule> rules = new ArrayList<>();
		packageArguments.add(new Argument("name"));
		types.add(new VariableType("Package", packageArguments));
		types.add(new VariableType("Class", arguments));
		types.add(new VariableType("Website", arguments));
		types.add(new VariableType("File", arguments));
		types.add(new VariableType("Component", arguments));
		types.add(new VariableType("XMLTag", arguments));
		types.add(new VariableType("Attribute", arguments));
		Environment env = new Environment(new ArrayList<Variable>(), types, rules);
		StateMachine dicto = new DictoBuilder(env).build().clone(env, new Context(context));
		dicto.run();
		return dicto.getResult();
	}
	
	@Test
	public void newIdentifierIncomplete() {
		StateResult stateResult = setUp("View");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void newIdentifierComplete() {
		StateResult stateResult = setUp("View = ");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void typeIncomplete() {
		StateResult stateResult = setUp("View = Pack");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void typeComplete() {
		StateResult stateResult = setUp("View = Package");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void typeCompleteWithWhitespace() {
		StateResult stateResult = setUp("View = Package ");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void withComplete() {
		StateResult stateResult = setUp("View = Package with");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void withIncomplete() {
		StateResult stateResult = setUp("View = Package wi");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void argNameIncomplete() {
		StateResult stateResult = setUp("View = Package with na");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void argNameComplete() {
		StateResult stateResult = setUp("View = Package with name");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void argComplete() {
		StateResult stateResult = setUp("View = Package with name : \"org.app.View\"");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void wrongArg() {
		StateResult stateResult = setUp("View = Package with stupid :");
		assertTrue(stateResult.toString(), stateResult.isError());
	}
	
	@Test
	public void argIncomplete() {
		StateResult stateResult = setUp("View = Package with name : ");
		assertTrue(stateResult.toString(), stateResult.isSuccess());
	}
	
	@Test
	public void multiArgIncomplete() {
		StateResult stateResult = setUp("View = Package with name : \"bla\", n");
		assertTrue(stateResult.toString(), stateResult.isSuccess());	
	}
	
	@Test
	public void multiArgComplete() {
		StateResult stateResult = setUp("View = Package with name : \"bla\", name : \"and still bla\"");
		assertTrue(stateResult.toString(), stateResult.isSuccess());	
	}
	
	@Test
	public void twoStatements() {
		StateResult stateResult = setUp("View = Package with name : \"bla\""
				+ "\nView = Package wi");
		assertTrue(stateResult.toString(), stateResult.isSuccess());	
	}
}
