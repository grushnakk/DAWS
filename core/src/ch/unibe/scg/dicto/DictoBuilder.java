package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_DIGITS;
import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_LETTERS;
import static ch.unibe.scg.dicto.parser.Acceptors.negRange;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.range;
import static ch.unibe.scg.dicto.parser.Acceptors.string;
import static ch.unibe.scg.dicto.parser.Acceptors.whitespace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Predicate;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.MultiStringAcceptor;
import ch.unibe.scg.dicto.states.LangError;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.State;
import ch.unibe.scg.dicto.states.StateMachine;
import ch.unibe.scg.dicto.states.StateResult;

public class DictoBuilder {

	private static final String REGION_IDENTIFIER = "ID";
	private static final String REGION_STRING_CONTENT = "SC";
	private static final String REGION_PREDICATE = "P";
	private static final String REGION_RULE = "R";

	private static final String CACHE_VAR_NAME = "VAR_NAME";
	private static final String CACHE_VAR_TYPE = "VAR_TYPE";
	private static final String CACHE_ARG_NAME = "ARG_NAME";
	private static final String CACHE_PREDICATE = "PREDICATE";

	private static final int ID_START = 00;
	private static final int ID_TYPE = 11;
	private static final int ID_KEYWORD_WITH = 12;
	private static final int ID_ARG_NAME = 13;
	private static final int ID_ARG_VALUE = 14;
	private static final int ID_AFTER_ARG_VALUE = 15;
	private static final int ID_PREDICATE = 21;
	private static final int ID_RULE = 22;
	private static final int ID_RULE_ARG = 23;

	private Environment env;
	private Acceptor idAcceptor;

	public DictoBuilder(Environment env) {
		setEnvironment(env);
	}

	public void setEnvironment(Environment env) {
		this.env = env;
	}

	public Environment getEnvironment() {
		return env;
	}

	public StateMachine build() {
		StateMachine dictoMachine = new StateMachine(ID_START);
		dictoMachine.addState(ID_START, new State(ID_START, new VarDefStartPath(), new RuleDefStartPath()));
		dictoMachine.addState(ID_TYPE, new State(ID_TYPE, new VarDefTypePath(env)));
		dictoMachine.addState(ID_KEYWORD_WITH, new State(ID_KEYWORD_WITH, new KeywordPath("with", ID_ARG_NAME)));
		dictoMachine.addState(ID_ARG_NAME, new State(ID_ARG_NAME, new VarDefArgNamePath()));
		dictoMachine.addState(ID_ARG_VALUE, new State(ID_ARG_VALUE, new VarDefArgStringPath()));
		dictoMachine.addState(ID_AFTER_ARG_VALUE, new State(ID_AFTER_ARG_VALUE, buildNextArgPath(), buildNextStatementPath()));
		dictoMachine.addState(ID_PREDICATE, new State(ID_PREDICATE, buildPredicatePath()));
		dictoMachine.addState(ID_RULE, new State(ID_RULE, buildRulePath()));
		dictoMachine.addState(ID_RULE_ARG, new State(ID_RULE_ARG, buildRuleArgVariablePath()));
		return dictoMachine;
	}

	private Path buildPredicatePath() {
		return new Path(new MultiStringAcceptor("can only", "cannot", "can", // TODO magic values
				"must").region(REGION_PREDICATE).chain(whitespace()),
				new NextAction() {

					@Override
					public StateResult onNext(Environment env,
							AcceptorResult result) {
						String predicate = result.getRegion(REGION_PREDICATE);
						env.writeCache(CACHE_PREDICATE, predicate);
						return new Next(ID_RULE);
					}
				}, noSuggestAction); // TODO replace suggestions
	}
	
	private Path buildNextStatementPath() {
		return new Path(new MultiStringAcceptor("\n", "\r"), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				String nameCache = env.readCache(CACHE_VAR_NAME);
				String typeCache = env.readCache(CACHE_VAR_TYPE);
				VariableType varType = env.getVariableType(typeCache);
				Map<String, String> args = new HashMap<>();
				for(Argument arg : varType.getArguments()) {
					if(env.hasCached(arg.getName()))
						args.put(arg.getName(), env.readCache(arg.getName()));
				}
				Variable var = new Variable(nameCache, varType, args);
				env.addVariable(var);
				env.resetCache();
				return new Next(ID_START);
			}
		}, noSuggestAction);
	}
	
	private Path buildNextArgPath() {
		return new Path(string(",").chain(optionalWhitespace()), new NextAction() {

			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				return new Next(ID_ARG_NAME);
			}
			
		}, noSuggestAction);
	}
	
	private Acceptor buildRuleAcceptor() {
		List<String> rules = new ArrayList<>();
		for(Rule rule : env.getRules())
			rules.add(rule.getName());
		System.out.println(rules);
		return new MultiStringAcceptor(rules).region(REGION_RULE);
	}
	
	private Path buildRulePath() {
		return new Path(buildRuleAcceptor().chain(optionalWhitespace()), new NextAction() {

			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				return new Next(ID_RULE_ARG); //TODO do some checking and storing
			}}, new RuleSuggestAction());
	}
	
	private Path buildRuleArgVariablePath() {
		return new Path(idAcceptor.chain(optionalWhitespace()), new NextAction() {

			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				// TODO Auto-generated method stub
				return null;
			}
			
		}, noSuggestAction); //TODO add a suggestAction
	}

	static class KeywordSuggestAction implements SuggestAction {

		private final List<String> list;

		KeywordSuggestAction(String keyword) {
			list = new ArrayList<>();
			list.add(keyword);
		}

		@Override
		public List<String> suggestions(Environment environment) {
			return list;
		}
	}
	
	static class RuleSuggestAction implements SuggestAction {

		@Override
		public List<String> suggestions(Environment env) {
			String varNameCache = env.readCache(CACHE_VAR_NAME);
			Variable var = env.getVariable(varNameCache);
			VariableType varType = var.getVariableType();
			Predicate predicate = Predicate.byCode(env.readCache(CACHE_PREDICATE));
			List<String> suggestions = new ArrayList<>();
			for(Rule rule : env.getRules()) {
				if(rule.canBeUsed(varType, predicate))
					suggestions.add(rule.getName());
			}
			return suggestions;
		}	
	}
}
