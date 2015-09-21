package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.*;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.MultiStringAcceptor;
import ch.unibe.scg.dicto.states2.StateMachine;
import ch.unibe.scg.dicto.states2.StateMachineBuilder;

public class Dicto {
	
	public static final String REGION_ID = "$ID";
	public static final String REGION_TYPE = "$TYPE";
	public static final String REGION_ARG_NAME ="$ARG_NAME";
	public static final String CACHE_NEW_VAR_NAME = "$ID";
	public static final String CACHE_TYPE = "$TYPE";
	public static final String CACHE_ARG_NAME ="$ARG_NAME";

	/*
	 * the names of the states
	 */
	private static final String STATE_START = "Start";
	private static final String STATE_VAR_TYPE = "VarType";
	private static final String STATE_ID_AFTER_ONLY = "AfterOnly";
	private static final String STATE_KEYWORD_WITH = "With";
	private static final String STATE_RULE = "Rule";
	private static final String STATE_ARG_NAME = "VarDefArgName";
	private static final String STATE_ARG_VALUE = "VarDefArgValue";
	private static final String STATE_AFTER_ARG = "VarDefAfterArg";
	/*
	 * some other stuff
	 */
	private static final Acceptor idAcceptor = range(RANGE_DIGITS + RANGE_LETTERS).repeat();
	
	public StateMachine build(Environment env) {
		StateMachineBuilder smBuilder = new StateMachineBuilder();
		smBuilder.startAt(STATE_START); // where do we start :D

		/*
		 * VAR DEF PATH
		 */
		smBuilder.state(STATE_START)
						.pathTo(STATE_VAR_TYPE)
						.accepts(idAcceptor.region(REGION_ID).chain(optionalWhitespace(), string("=")))
						.onSuccess(new StoreVarNameAction())
						.startWithOptionalWhitespace()
						.complete();
		smBuilder.state(STATE_VAR_TYPE)
						.pathTo(STATE_KEYWORD_WITH)
						.accepts(typeAcceptor(env).region(REGION_TYPE))
						.suggests(new VarTypeSuggestor())
						.onSuccess(new TypeCheckerAction())
						.startWithOptionalWhitespace()
						.complete();
		smBuilder.state(STATE_KEYWORD_WITH)
						.pathTo(STATE_ARG_NAME)
						.accepts(string("with"))
						.suggests("with")
						.complete();
		smBuilder.state(STATE_ARG_NAME)
						.pathTo(STATE_ARG_VALUE)
						.accepts(idAcceptor.region(REGION_ARG_NAME).chain(optionalWhitespace(), string(":")))
						.suggests(new ArgNameSuggestor())
						.onSuccess(new ArgNameCheckerAction())
						.complete();
		smBuilder.state(STATE_ARG_VALUE)
						.pathTo(STATE_AFTER_ARG)
						.accepts(string("\"").chain(negRange("\"").repeat(), string("\"")))
						.complete();
		smBuilder.state(STATE_AFTER_ARG)
						.pathTo(STATE_ARG_NAME)
						.accepts(string(","))
						.startWithOptionalWhitespace()
						.complete();
		smBuilder.state(STATE_AFTER_ARG)
						.pathTo(STATE_START)
						.accepts(new MultiStringAcceptor("\n", "\r"))
						.onSuccess(new NewVariableAction())
						.startWithOptionalWhitespace()
						.complete();
		/*
		 * ONLY PATH
		 */
		smBuilder.state(STATE_START)
						.pathTo(STATE_ID_AFTER_ONLY)
						.accepts(string("only"))
						.suggests("only")
						.complete();
		/*
		 * NORMAL RULE PATH
		 */
		smBuilder.state(STATE_START)
						.pathTo(STATE_RULE)
						.accepts(idAcceptor)
						//TODO suggests all existing variables
						.complete();
								
		
		smBuilder.state(STATE_ID_AFTER_ONLY);
		smBuilder.state(STATE_RULE);
		return smBuilder.build();
	}
	
	Acceptor typeAcceptor(Environment env) {
		List<String> types = new ArrayList<>();
		for(VariableType type : env.getVariableTypes()) {
			types.add(type.getName());
		}
		return new MultiStringAcceptor(types);
	}
}
