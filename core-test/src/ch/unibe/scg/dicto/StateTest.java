package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.State.StateBuilder.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class StateTest {

	State startState;
	ArrayList<Token> tokens; 
	
	@Before
	public void setUp() {
		startState = 
			start().successor(TokenType.IDENTIFIER, 
				state().successor(TokenType.SYMBOL_COLON
						, state().successor(TokenType.KEYWORD_WEBSITE
								, state().successor(TokenType.KEYWORD_WITH
										, state().successor(TokenType.KEYWORD_URL
												, state().successor(TokenType.SYMBOL_BRACKET_L
														, state().successor(TokenType.VALUE_STRING
																, state().successor(TokenType.SYMBOL_BRACKET_R
																		, state().setEndState())))))))).build();
		tokens = new ArrayList<Token>(){{
			add(new Token(TokenType.IDENTIFIER, "youtube"));
			add(new Token(TokenType.SYMBOL_COLON, ":"));
			add(new Token(TokenType.KEYWORD_WEBSITE, "Website"));
			add(new Token(TokenType.KEYWORD_WITH, "with"));
			add(new Token(TokenType.KEYWORD_URL, "url"));
			add(new Token(TokenType.SYMBOL_BRACKET_L, "("));
			add(new Token(TokenType.VALUE_STRING, "\"youtube.com\""));
			add(new Token(TokenType.SYMBOL_BRACKET_R, ")"));
		}};
	}
	
	@Test
	public void testValid() throws UnexpectedTokenException {
		State result = startState.consume(0, tokens);
		assertTrue(result.isEndState());
	}
}
