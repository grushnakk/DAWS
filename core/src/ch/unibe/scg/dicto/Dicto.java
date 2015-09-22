package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.*;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.MultiStringAcceptor;
import ch.unibe.scg.dicto.states2.StateMachine;
import ch.unibe.scg.dicto.states2.StateMachineBuilder;

public class Dicto {
	
	public static final String REGION_ID = "$ID";
	public static final String REGION_TYPE = "$TYPE";
	public static final String REGION_ARG_NAME ="$ARG_NAME";
	public static final String REGION_PREDICATE = "$PREDICATE";
	public static final String REGION_RULE = "$RULE";
	public static final String REGION_PARAMETER = "$PARAMETER";
	public static final String CACHE_NEW_VAR_NAME = "$ID";
	public static final String CACHE_TYPE = "$TYPE";
	public static final String CACHE_ARG_NAME ="$ARG_NAME";
	public static final String CACHE_PREDICATE = "$PREDICATE";
	public static final String CACHE_RULE = "$RULE";

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
	private static final String STATE_RULE_PREDICATE = "RulePredicate";
	private static final String STATE_RULE_PARAMETER = "RuleParameter";
	private static final String STATE_RULE_AFTER_PARAMETER = "RuleAfterParameter";
	private static final String STATE_RULE_MULTI_SUBJECT = "RuleMultiSubject";
	private static final String STATE_ONLY_CAN = "RuleCan";
	private static final String STATE_ONLY_MULTI_SUBJECT = "RuleOnlyMultiSubject";
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
						.startWithOptionalWhitespace()
						.complete();
		smBuilder.state(STATE_ID_AFTER_ONLY)
						.pathTo(STATE_ONLY_CAN)
						.accepts(idAcceptor.region(REGION_ID))
						.suggests(new VariableSuggestor())
						.onSuccess(new VarLookUpAction())
						.complete();
		smBuilder.state(STATE_ONLY_CAN)
						.pathTo(STATE_ONLY_MULTI_SUBJECT)
						.accepts(string(","))
						.startWithOptionalWhitespace()
						.complete();
		smBuilder.state(STATE_ONLY_CAN)
						.pathTo(STATE_RULE)
						.accepts(string("can"))
						.suggests("can")
						.onSuccess(new StoreOnlyCanAction())
						.complete();
		smBuilder.state(STATE_ONLY_MULTI_SUBJECT)
						.pathTo(STATE_ONLY_CAN)
						.accepts(idAcceptor.region(REGION_ID))
						.suggests(new VariableOfSameTypeSuggestor())
						.onSuccess(new VarSameTypeCheckerAction())
						.complete();
		/*
		 * NORMAL RULE PATH
		 */
		smBuilder.state(STATE_START)
						.pathTo(STATE_RULE_PREDICATE)
						.accepts(idAcceptor.region(REGION_ID))
						.suggests(new VariableSuggestor())
						.onSuccess(new VarLookUpAction())
						.startWithOptionalWhitespace()
						.complete();
		smBuilder.state(STATE_RULE_PREDICATE)
						.pathTo(STATE_RULE)
						.accepts(new MultiStringAcceptor(new PredicateSuggestor().suggestions(env)).region(REGION_PREDICATE))
						.suggests(new PredicateSuggestor())
						.onSuccess(new StorePredicateAction())
						.complete();
		smBuilder.state(STATE_RULE_PREDICATE)
						.pathTo(STATE_RULE_MULTI_SUBJECT)
						.accepts(string(","))
						.startWithOptionalWhitespace()
						.complete();
		smBuilder.state(STATE_RULE_MULTI_SUBJECT)
						.pathTo(STATE_RULE_PREDICATE)
						.accepts(idAcceptor.region(REGION_ID))
						.suggests(new VariableOfSameTypeSuggestor())
						.onSuccess(new VarSameTypeCheckerAction())
						.complete();
		smBuilder.state(STATE_RULE)
						.pathTo(STATE_RULE_PARAMETER)
						.accepts(ruleAcceptor(env).region(REGION_RULE))
						.suggests(new RuleSuggestor())
						.onSuccess(new StoreRuleAction())
						.complete();
		smBuilder.state(STATE_RULE_PARAMETER)
						.pathTo(STATE_START)
						.accepts(new MultiStringAcceptor("\n", "\r"))
						.startWithOptionalWhitespace()
						.onSuccess(new ResetCacheAction())
						.complete();
		smBuilder.state(STATE_RULE_PARAMETER)
						.pathTo(STATE_RULE_AFTER_PARAMETER)
						.accepts(string("\"").chain(negRange("\"").repeat(), string("\"")))
						.complete();
		smBuilder.state(STATE_RULE_PARAMETER)
						.pathTo(STATE_RULE_AFTER_PARAMETER)
						.accepts(idAcceptor.region(REGION_PARAMETER))
						.suggests(new VariableSuggestor())
						.onSuccess(new VarCheckerAction())
						.complete();
		smBuilder.state(STATE_RULE_AFTER_PARAMETER)
						.pathTo(STATE_START)
						.accepts(new MultiStringAcceptor("\n", "\r"))
						.startWithOptionalWhitespace()
						.onSuccess(new ResetCacheAction())
						.complete();
		return smBuilder.build();
	}
	
	Acceptor typeAcceptor(Environment env) {
		List<String> types = new ArrayList<>();
		for(VariableType type : env.getVariableTypes()) {
			types.add(type.getName());
		}
		return new MultiStringAcceptor(types);
	}
	
	//TODO I'm worried about the ordering of the rules :(
	Acceptor ruleAcceptor(Environment env) {
		List<String> rules = new ArrayList<>();
		for(VariableType type : env.getVariableTypes()) {
			for(Rule rule : type.getRules()) {
				rules.add(rule.getName());
			}
		}
		return new MultiStringAcceptor(rules);
	}
}