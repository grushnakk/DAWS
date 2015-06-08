package ch.unibe.scg.dicto.states;

import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_DIGITS;
import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_LETTERS;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.range;
import static ch.unibe.scg.dicto.parser.Acceptors.string;
import static ch.unibe.scg.dicto.parser.Acceptors.whitespace;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.Context;

public class StateTests {
	
	State state;
	
	@Before
	public void setUp() {
		Acceptor idAcceptor = range(RANGE_DIGITS + RANGE_LETTERS).repeat();
		SuggestAction noSuggestions = new SuggestAction() {
			
			@Override
			public List<String> suggestions(Environment environment) {
				return new ArrayList<>();
			}
		};
		Path path1 = new Path(idAcceptor.chain(optionalWhitespace(), string("="), optionalWhitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				return new Next(5);
			}
		}, noSuggestions);		
		Path path2 = new Path(idAcceptor.chain(whitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				return new LangError("random Error");
			}
		}, noSuggestions);
		
		state = new State(path1, path2);
	}
	
	@Test
	public void incomplete() {
		StateResult actual = state.process(new Context("View"), env());
		assertTrue(actual.isSuccess());
	}
	
	@Test
	public void complete() {
		StateResult actual = state.process(new Context("View = "), env());
		assertTrue(actual.toString(), actual.isNext());
	}
	
	Environment env() {
		return new Environment(new ArrayList<Rule>(), new ArrayList<Variable>(), new ArrayList<VariableType>());
	}
}
