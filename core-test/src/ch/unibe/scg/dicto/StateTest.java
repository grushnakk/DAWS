package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.State.StateBuilder.*;

import org.junit.Before;

public class StateTest {

	State startState;
	
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
	}
}
