package ch.unibe.scg.dicto.parser;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.parser.AcceptorResult.State;
import static ch.unibe.scg.dicto.parser.Acceptors.*;
import static org.junit.Assert.assertEquals;

public class AcceptorsTests {

	private Acceptor acceptorABC;
	private Acceptor acceptorUnknownID;
	private Acceptor acceptorType;
	private Acceptor acceptorKeyword;
	private Acceptor acceptorString;
	
	@Before
	public void setUp() {		
		acceptorABC = range("abc").repeat().region("VAR_NAME").chain(optionalWhitespace(), string(":"), optionalWhitespace());
		acceptorUnknownID = range(RANGE_LETTERS + RANGE_DIGITS + "_").repeat().region("VAR_NAME")
				.chain(optionalWhitespace(), string(":"), optionalWhitespace());
		acceptorType = range(RANGE_LETTERS + RANGE_DIGITS).repeat().region("TYPE").chain(whitespace());
		acceptorKeyword = string("with").chain(whitespace());
		acceptorString = string("\"").chain(negRange("\"").repeat(), string("\""), optionalWhitespace());
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
		AcceptorResult expected = new AcceptorResult(6, 13, State.INCOMPLETE);
		expected.addRegion("TYPE", "Package");
		assertEquals(expected, actual);
	}
	
	@Test
	public void incompleteType() {
		Context in = new Context("");
		AcceptorResult actual = acceptorType.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 0, State.INCOMPLETE);
		expected.addRegion("TYPE", "");
		assertEquals(expected, actual);
	}
	
	@Test
	public void incompleteKeyword() {
		Context in = new Context("View: Package wi");
		in.incrementIndex(14);
		AcceptorResult actual = acceptorKeyword.accept(in);
		AcceptorResult expected = new AcceptorResult(14, 16, State.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void completeKeyword() {
		Context in = new Context("View: Package with");
		in.incrementIndex(14);
		AcceptorResult actual = acceptorKeyword.accept(in);
		AcceptorResult expected = new AcceptorResult(14, 18, State.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void completeKeywordWithWhitespace() {
		Context in = new Context("View: Package with  ");
		in.incrementIndex(14);
		AcceptorResult actual = acceptorKeyword.accept(in);
		AcceptorResult expected = new AcceptorResult(14, 20, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void completeString() {
		Context in = new Context("\"blabla\"");
		AcceptorResult actual = acceptorString.accept(in);
		AcceptorResult expected = new AcceptorResult(0, 8, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void whitespaceIncomplete() {
		Context in = new Context("");
		AcceptorResult actual = Acceptors.whitespace().accept(in);
		AcceptorResult expected = new AcceptorResult(0, 0, State.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void whitespaceComplete() {
		Context in = new Context(" ");
		AcceptorResult actual = Acceptors.whitespace().accept(in);
		AcceptorResult expected = new AcceptorResult(0, 1, State.SUCCESS);
		assertEquals(expected, actual);
	}
}
