package ch.unibe.scg.dicto.states;

import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_DIGITS;
import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_LETTERS;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.range;
import static ch.unibe.scg.dicto.parser.Acceptors.string;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.Context;

public class PathTests {

	private Path path;	
	
	@Before
	public void setUp() {		
		path = new Path(
				range(RANGE_LETTERS + RANGE_DIGITS + "_").repeat().region("VAR_NAME").chain(optionalWhitespace(), string(":"), optionalWhitespace())){

					@Override
					public StateResult onNext(Environment env, AcceptorResult result) {
						String name = result.getRegion("VAR_NAME");
						if(env.isVariableDefined(name))
							return new LangError("Variable already defined: " + name);
						env.writeCache("VAR_NAME", name);
						return new Next(1); //switch to state with id 1
					}

					@Override
					public List<String> suggestions(Environment env) {
						return  new ArrayList<>();
					}
			
		};
	}
	
	@Test
	public void nextSucceed() {
		AcceptorResult accResult = path.accept(new Context("hallo:"));
		StateResult actual = path.onNext(new Environment(new ArrayList<Variable>(), new ArrayList<VariableType>()), accResult);
		StateResult expected = new Next(1);
		assertEquals(expected, actual);
	}
}
