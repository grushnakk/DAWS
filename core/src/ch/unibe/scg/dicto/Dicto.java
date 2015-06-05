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

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.AcceptorResult;
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
	private static final int ID_TYPE 				= 01;
	private static final int ID_KEYWORD_WITH 		= 02;
	private static final int ID_ARG_NAME			= 03;
	private static final int ID_ARG_ASSIGN			= 04;
	private static final int ID_ARG_VALUE			= 05;
//	private static final int ID_RULE 				= 10;
//	private static final int ID_KNOW_ID_AFTER_ONLY 	= 20;
	
	private static final String REGION_IDENTIFIER = "ID";
	private static final String CACHE_VAR_NAME = "VAR_NAME";
	private static final String CACHE_VAR_TYPE = "VAR_TYPE";
	
	private static SuggestAction NO_SUGGESTIONS;
	private static Acceptor IDENTIFIER_ACCEPTOR;
	private static Path NEW_ID_PATH;
	private static Path TYPE_PATH;
	private static Path WITH_PATH;
	private static Path ARG_NAME_PATH;
	private static Path ARG_ASSIGN_PATH;
	private static Path ARG_STRING_PATH;
	
	static {
		buildParts();
		//assemble everything
		DICTO_MACHINE = new StateMachine(ID_START);
		DICTO_MACHINE.addState(ID_START, new State(NEW_ID_PATH));
		DICTO_MACHINE.addState(ID_TYPE, new State(TYPE_PATH));
		DICTO_MACHINE.addState(ID_KEYWORD_WITH, new State(WITH_PATH));
		DICTO_MACHINE.addState(ID_ARG_NAME, new State(ARG_NAME_PATH));
		DICTO_MACHINE.addState(ID_ARG_ASSIGN, new State(ARG_ASSIGN_PATH));
		DICTO_MACHINE.addState(ID_ARG_VALUE, new State(ARG_STRING_PATH)); //multiple different values possible
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
		}, new KeywordSuggestAction("="));		
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
		ARG_NAME_PATH = new Path(IDENTIFIER_ACCEPTOR.chain(optionalWhitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				return new Next(ID_ARG_ASSIGN);
			}
		}, NO_SUGGESTIONS);
		ARG_ASSIGN_PATH = new Path(string(":").chain(optionalWhitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				return new Next(ID_ARG_VALUE);
			}
		}, new KeywordSuggestAction(":"));
		ARG_STRING_PATH = new Path(string("\"").chain(negRange("\"").repeat(), string("\""), optionalWhitespace()), new NextAction() {
			
			@Override
			public StateResult onNext(Environment env, AcceptorResult result) {
				return new Next(ID_START);
			}
		}, NO_SUGGESTIONS);
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
}
