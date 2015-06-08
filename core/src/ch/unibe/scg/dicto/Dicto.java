package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_DIGITS;
import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_LETTERS;
import static ch.unibe.scg.dicto.parser.Acceptors.negRange;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;
import static ch.unibe.scg.dicto.parser.Acceptors.range;
import static ch.unibe.scg.dicto.parser.Acceptors.string;
import static ch.unibe.scg.dicto.parser.Acceptors.whitespace;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.MultiStringAcceptor;
import ch.unibe.scg.dicto.states.LangError;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.NextAction;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.State;
import ch.unibe.scg.dicto.states.StateMachine;
import ch.unibe.scg.dicto.states.StateResult;
import ch.unibe.scg.dicto.states.SuggestAction;

public class Dicto {

	public static final StateMachine DICTO_MACHINE;
	
	private static final int ID_START 				= 00;
	private static final int ID_TYPE 				= 11;
	private static final int ID_KEYWORD_WITH 		= 12;
	private static final int ID_ARG_NAME			= 13;
	private static final int ID_ARG_VALUE			= 14;
	private static final int ID_PREDICATE           = 21;
	private static final int ID_RULE 				= 22;
	
	private static final String REGION_IDENTIFIER = "ID";
	private static final String REGION_STRING_CONTENT = "SC";
	private static final String REGION_PREDICATE = "P";
	
	private static final String CACHE_VAR_NAME = "VAR_NAME";
	private static final String CACHE_VAR_TYPE = "VAR_TYPE";
	private static final String CACHE_ARG_NAME = "ARG_NAME";
	private static final String CACHE_PREDICATE = "PREDICATE";
	
	private static SuggestAction NO_SUGGESTIONS;
	private static Acceptor IDENTIFIER_ACCEPTOR;
	private static Path NEW_ID_PATH;
	private static Path KNOWN_ID_PATH;
	private static Path PREDICATE_PATH;
	private static Path TYPE_PATH;
	private static Path WITH_PATH;
	private static Path ARG_NAME_PATH;
	private static Path ARG_STRING_PATH;
	private static Path RULE_PATH;
	
	static {
		buildParts();
		//assemble everything
		DICTO_MACHINE = new StateMachine(ID_START);
		DICTO_MACHINE.addState(ID_START, new State(NEW_ID_PATH, KNOWN_ID_PATH));
		DICTO_MACHINE.addState(ID_TYPE, new State(TYPE_PATH));
		DICTO_MACHINE.addState(ID_KEYWORD_WITH, new State(WITH_PATH));
		DICTO_MACHINE.addState(ID_ARG_NAME, new State(ARG_NAME_PATH));
		DICTO_MACHINE.addState(ID_ARG_VALUE, new State(ARG_STRING_PATH)); //multiple different values possible
		DICTO_MACHINE.addState(ID_PREDICATE, new State(PREDICATE_PATH));
		DICTO_MACHINE.addState(ID_RULE, new State(RULE_PATH));
	}
	
	static void buildParts() {
		IDENTIFIER_ACCEPTOR = range(RANGE_DIGITS + RANGE_LETTERS).repeat().region(REGION_IDENTIFIER);
		NO_SUGGESTIONS = new SuggestAction() {
			
			@Override
			public List<String> suggestions(Environment environment) {
				return new ArrayList<>();
			}
		};
		NEW_ID_PATH = new Path(IDENTIFIER_ACCEPTOR.chain(optionalWhitespace(), string("="), optionalWhitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				env.writeCache(CACHE_VAR_NAME, result.getRegion(REGION_IDENTIFIER));
				return new Next(ID_TYPE);
			}
		}, NO_SUGGESTIONS);		
		KNOWN_ID_PATH = new Path(IDENTIFIER_ACCEPTOR.chain(whitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				String varName = result.getRegion(REGION_IDENTIFIER);
				if(env.isVariableDefined(varName)) {
					env.writeCache(CACHE_VAR_NAME, varName);
					return new Next(ID_PREDICATE);
				}
				return new LangError("unknown Variable " + varName);
			}
		}, NO_SUGGESTIONS); //TODO replace suggestions
		TYPE_PATH = new Path(IDENTIFIER_ACCEPTOR.chain(whitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				String varType = result.getRegion(REGION_IDENTIFIER);
				if(env.isTypeDefined(varType)) {
					env.writeCache(CACHE_VAR_TYPE, varType);
					return new Next(ID_KEYWORD_WITH);
				} else {
					return new LangError("unknown variable type: " + varType);
				}
			}
		}, new VariableTypesSuggestAction());
		WITH_PATH = new Path(string("with").chain(whitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				return new Next(ID_ARG_NAME);
			}
		}, new KeywordSuggestAction("with"));
		ARG_NAME_PATH = new Path(IDENTIFIER_ACCEPTOR.chain(optionalWhitespace(), string(":"), optionalWhitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				VariableType type = env.getVariableType(env.readCache(CACHE_VAR_TYPE));
				String argName = result.getRegion(REGION_IDENTIFIER);
				if(!type.hasArgument(argName)) {
					return new LangError("unknown argument for type " + type + ": " + argName);
				}
				env.writeCache(CACHE_ARG_NAME, argName);
				return new Next(ID_ARG_VALUE);
			}
		}, new ArgNameSuggestAction());
		ARG_STRING_PATH = new Path(string("\"").chain(negRange("\"").repeat().region(REGION_STRING_CONTENT), string("\""), optionalWhitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				String content = result.getRegion(REGION_STRING_CONTENT);
				String argName = env.readCache(CACHE_ARG_NAME);
				env.writeCache(argName, content);
				return new Next(ID_START);
			}
		}, NO_SUGGESTIONS);
		PREDICATE_PATH = new Path(new MultiStringAcceptor("can only", "cannot", "can", "must").region(REGION_PREDICATE).chain(whitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				String predicate = result.getRegion(REGION_PREDICATE);
				env.writeCache(CACHE_PREDICATE, predicate);
				return new Next(ID_RULE);
			}
		}, NO_SUGGESTIONS); //TODO replace suggestions
	}
	
	static class VariableTypesSuggestAction implements SuggestAction {

		@Override
		public List<String> suggestions(Environment environment) {
			List<String> suggestions = new ArrayList<>();
			for(VariableType type : environment.getVariableTypes())
				suggestions.add(type.getName());
			return suggestions;
		}
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
	
	static class ArgNameSuggestAction implements SuggestAction {
		
		@Override
		public List<String> suggestions(Environment environment) {
			String typeName = environment.readCache(CACHE_VAR_TYPE);
			VariableType type = environment.getVariableType(typeName);
			List<String> names = new ArrayList<>();
			for(Argument arg : type.getArguments()) {
				names.add(arg.getName());
			}
			return names;
		}
	}
}
