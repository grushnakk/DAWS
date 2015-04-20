package ch.unibe.scg.dicto;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Context;
import ch.unibe.scg.dicto.states.StateMachine;
import ch.unibe.scg.dicto.states.StateResult;

public class DictoTest {
	
	public StateResult setUp(String context) {
		Environment env = new Environment(new ArrayList<Rule>(), new ArrayList<Variable>(), new ArrayList<VariableType>());
		StateMachine dicto =  Dicto.DICTO_MACHINE.clone(env, new Context(context));
		dicto.run();
		return dicto.getResult();
	}
	
	@Test
	public void newIdentifierIncomplete() {
		StateResult stateResult = setUp("View");
		assertTrue(stateResult.isSuccess());
		assertEquals(new ArrayList<>(), stateResult.getSuggestions());
	}
	
	@Test
	public void newIdentifierComplete() {
		StateResult stateResult = setUp("View = ");
		assertTrue(stateResult.isSuccess());
	}
	
	@Test
	public void typeIncomplete() {
		StateResult stateResult = setUp("View = Pack");
		assertTrue(stateResult.isSuccess());
	}
	
	@Test
	public void typeComplete() {
		StateResult stateResult = setUp("View = Package");
		System.out.println(stateResult);
		assertTrue(stateResult.isSuccess());
	}
	
	@Test
	public void typeCompleteWithWhitespace() {
		StateResult stateResult = setUp("View = Package ");
		System.out.println(stateResult);
		assertTrue(stateResult.isSuccess());
	}
	
	@Test
	public void withComplete() {
		StateResult stateResult = setUp("View = Package with");
		assertTrue(stateResult.isSuccess());
	}
	
	@Test
	public void withIncomplete() {
		StateResult stateResult = setUp("View = Package wi");
		assertTrue(stateResult.isSuccess());
	}
	
	@Test
	public void argNameIncomplete() {
		StateResult stateResult = setUp("View = Package with na");
		assertTrue(stateResult.isSuccess());
	}
	
	@Test
	public void argNameComplete() {
		StateResult stateResult = setUp("View = Package with name");
		assertTrue(stateResult.isSuccess());
	}
	
	@Test
	public void argComplete() {
		StateResult stateResult = setUp("View = Package with name=\"org.app.View\"");
		assertTrue(stateResult.isSuccess());
	}
	
	@Test
	public void argIncomplete() {
		StateResult stateResult = setUp("View = Package with name=");
		assertTrue(stateResult.isSuccess());
	}
}
