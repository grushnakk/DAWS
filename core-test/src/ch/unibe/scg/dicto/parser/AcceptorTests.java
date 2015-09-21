package ch.unibe.scg.dicto.parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.AcceptorResult.Type;

public class AcceptorTests {

	private Acceptor complexAcc;
	private Acceptor abcRange;
	private Acceptor strAcc;
	private Acceptor repeatAcc;
	private Acceptor regionAcc;
	private Acceptor negRangeAcc;
	
	@Before
	public void setUp() {
		complexAcc = new ChainAcceptor(
				new RepeatAcceptor(new RangeAcceptor("abc")), 
				new OptionalAcceptor(new RepeatAcceptor(new RangeAcceptor(" \t\r\n"))), 
				new StringAcceptor(":"));
		regionAcc = new ChainAcceptor(
				new RegionAcceptor("VAR_NAME", new RepeatAcceptor(new RangeAcceptor("abc"))), 
				new OptionalAcceptor(new RepeatAcceptor(new RangeAcceptor(" \t\r\n"))), 
				new StringAcceptor(":"));
		abcRange = new RangeAcceptor("abc");
		strAcc = new StringAcceptor("hello");
		repeatAcc = new RepeatAcceptor(abcRange);
		negRangeAcc = new NegativeRangeAcceptor("a");
	}
		
	@Test
	public void full() {
		String input = "abcca :";
		AcceptorResult actual = complexAcc.accept(new Context(input));
		AcceptorResult expected = new AcceptorResult(0, 7, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void region() {
		String input = "abcca :";
		AcceptorResult actualResult = regionAcc.accept(new Context(input));
		String actual = actualResult.getRegion("VAR_NAME");
		String expected = "abcca";
		assertEquals(expected, actual);
		AcceptorResult expectedResult = new AcceptorResult(0, 7, Type.SUCCESS);
		expectedResult.addRegion("VAR_NAME", "abcca");
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void rangeA() {
		Context context = new Context("a");
		AcceptorResult actual = abcRange.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 1, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeC() {
		Context context = new Context("c");
		AcceptorResult actual = abcRange.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 1, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeD() {
		Context context = new Context("d");
		AcceptorResult actual = abcRange.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 0, Type.FAILURE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeAA() {
		Context context = new Context("aa");
		AcceptorResult actual = abcRange.accept(context, new AcceptorResult(0, 1, Type.SUCCESS));
		AcceptorResult expected = new AcceptorResult(0, 2, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeIncomplete1() {
		Context context = new Context("");
		AcceptorResult actual = abcRange.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 0, Type.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeIncomplete2() {
		Context context = new Context("ab");
		AcceptorResult actual = abcRange.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 1, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringHello() {
		Context context = new Context("hello");
		AcceptorResult actual = strAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 5, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringHell() {
		Context context = new Context("hell");
		AcceptorResult actual = strAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 4, Type.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringBello() {
		Context context = new Context("bello");
		AcceptorResult actual = strAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 0, Type.FAILURE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringbbbHello() {
		Context context = new Context("bbbhello");
		AcceptorResult actual = strAcc.accept(context, new AcceptorResult(0, 3, Type.SUCCESS));
		AcceptorResult expected = new AcceptorResult(0, 8, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringbbbHell() {
		Context context = new Context("bbbhell");
		AcceptorResult actual = strAcc.accept(context, new AcceptorResult(0, 3, Type.SUCCESS));
		AcceptorResult expected = new AcceptorResult(0, 7, Type.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringbbbBello() {
		Context context = new Context("bbbBello");
		AcceptorResult actual = strAcc.accept(context, new AcceptorResult(0, 3, Type.SUCCESS));
		AcceptorResult expected = new AcceptorResult(0, 3, Type.FAILURE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeatcb() {
		Context context = new Context("cb");
		AcceptorResult actual = repeatAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 2, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeatcba() {
		Context context = new Context("cba");
		AcceptorResult actual = repeatAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 3, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeatcbad() {
		Context context = new Context("cbad");
		AcceptorResult actual = repeatAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 3, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeat() {
		Context context = new Context("");
		AcceptorResult actual = repeatAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 0, Type.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeatd() {
		Context context = new Context("d");
		AcceptorResult actual = repeatAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 0, Type.FAILURE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void negRangeSuccess() {
		Context context = new Context("d");
		AcceptorResult actual = negRangeAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 1, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void negRangeFailure() {
		Context context = new Context("a");
		AcceptorResult actual = negRangeAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 0, Type.FAILURE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void negRangeIncomplete1() {
		Context context = new Context("");
		AcceptorResult actual = negRangeAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 0, Type.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void negRangeIncomplete2() {
		Context context = new Context("db");
		AcceptorResult actual = negRangeAcc.accept(context);
		AcceptorResult expected = new AcceptorResult(0, 1, Type.SUCCESS);
		assertEquals(expected, actual);
	}
}
