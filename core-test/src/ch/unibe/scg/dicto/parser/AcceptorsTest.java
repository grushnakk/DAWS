package ch.unibe.scg.dicto.parser;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.parser.AcceptorResult.State;
import static ch.unibe.scg.dicto.parser.Acceptors.*;
import static org.junit.Assert.assertEquals;

public class AcceptorsTest {

	private Acceptor acceptor;
	private Acceptor acceptor2;
	
	@Before
	public void setUp() {		
		acceptor = range("abc").repeat().region("VAR_NAME").chain(optionalWhitespace(), string(":"), optionalWhitespace());
		acceptor2 = range(RANGE_LETTERS + RANGE_DIGITS + "_").repeat().region("VAR_NAME")
				.chain(optionalWhitespace(), string(":"), optionalWhitespace());
	}
	
	@Test
	public void success() {
		Context in = new Context("abbac: ");
		AcceptorResult actual = acceptor.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 7, State.SUCCESS);
		expected.addRegion("VAR_NAME", "abbac");
		assertEquals(expected, actual);
	}
	
	@Test
	public void incomplete() {
		Context in = new Context("abba ");
		AcceptorResult actual = acceptor.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 5, State.INCOMPLETE);
		expected.addRegion("VAR_NAME", "abba");
		assertEquals(expected, actual);
	}
	
	@Test
	public void failure() {
		Context in = new Context("abbac = ");
		AcceptorResult actual = acceptor.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 6, State.FAILURE);
		expected.addRegion("VAR_NAME", "abbac");
		assertEquals(expected, actual);
	}
	
	@Test
	public void success2() {
		Context in = new Context("hallo:");
		AcceptorResult actual = acceptor2.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 6, State.SUCCESS);
		expected.addRegion("VAR_NAME", "hallo");
		assertEquals(expected, actual);
	}
}
