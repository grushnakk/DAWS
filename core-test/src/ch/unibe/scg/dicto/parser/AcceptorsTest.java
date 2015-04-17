package ch.unibe.scg.dicto.parser;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.parser.AcceptorResult.State;
import static ch.unibe.scg.dicto.parser.Acceptors.*;
import static org.junit.Assert.assertEquals;

public class AcceptorsTest {

	private Acceptor acceptorABC;
	private Acceptor acceptorUnknownID;
	private Acceptor acceptorType;
	
	@Before
	public void setUp() {		
		acceptorABC = range("abc").repeat().region("VAR_NAME").chain(optionalWhitespace(), string(":"), optionalWhitespace());
		acceptorUnknownID = range(RANGE_LETTERS + RANGE_DIGITS + "_").repeat().region("VAR_NAME")
				.chain(optionalWhitespace(), string(":"), optionalWhitespace());
		acceptorType = range(RANGE_LETTERS + RANGE_DIGITS).repeat().region("TYPE").chain(optionalWhitespace());
	}
	
	@Test
	public void success() {
		Context in = new Context("abbac: ");
		AcceptorResult actual = acceptorABC.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 7, State.SUCCESS);
		expected.addRegion("VAR_NAME", "abbac");
		assertEquals(expected, actual);
	}
	
	@Test
	public void incomplete() {
		Context in = new Context("abba ");
		AcceptorResult actual = acceptorABC.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 5, State.INCOMPLETE);
		expected.addRegion("VAR_NAME", "abba");
		assertEquals(expected, actual);
	}
	
	@Test
	public void failure() {
		Context in = new Context("abbac = ");
		AcceptorResult actual = acceptorABC.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 6, State.FAILURE);
		expected.addRegion("VAR_NAME", "abbac");
		assertEquals(expected, actual);
	}
	
	@Test
	public void success2() {
		Context in = new Context("hallo:");
		AcceptorResult actual = acceptorUnknownID.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 6, State.SUCCESS);
		expected.addRegion("VAR_NAME", "hallo");
		assertEquals(expected, actual);
	}
	
	@Test
	public void unknownIDcomplete() {
		Context in = new Context("View : ");
		AcceptorResult actual = acceptorUnknownID.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 7, State.SUCCESS);
		expected.addRegion("VAR_NAME", "View");
		assertEquals(expected, actual);
	}
	
	@Test
	public void unknownIDincomplete() {
		Context in = new Context("");
		AcceptorResult actual = acceptorUnknownID.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 0, State.INCOMPLETE);
		expected.addRegion("VAR_NAME", "");
		assertEquals(expected, actual);
	}
	
	@Test
	public void successType() {
		Context in = new Context("View: Package");
		in.incrementIndex(6);
		AcceptorResult actual = acceptorType.accept(in);
		AcceptorResult expected = new AcceptorResult(6, 13, State.SUCCESS);
		expected.addRegion("TYPE", "Package");
		assertEquals(expected, actual);
	}
}
