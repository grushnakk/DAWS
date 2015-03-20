package ch.unibe.scg.dicto.parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.Context;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.Result.State;

public class AcceptorTest {

	private Acceptor complexAcc;
	private Acceptor abcRange;
	private Acceptor strAcc;
	private Acceptor repeatAcc;
	
	@Before
	public void setUp() {
		complexAcc = new ChainAcceptor(
				new RepeatAcceptor(new RangeAcceptor("abc")), 
				new OptionalAcceptor(new RepeatAcceptor(new RangeAcceptor(" \t\r\n"))), 
				new StringAcceptor(":"));
		abcRange = new RangeAcceptor("abc");
		strAcc = new StringAcceptor("hello");
		repeatAcc = new RepeatAcceptor(abcRange);
	}
	
	@Test
	public void full() {
		String input = "abcca :";
		Result actual = complexAcc.accept(new Context(input));
		Result expected = new Result(0, 7, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeA() {
		Context context = new Context("a");
		Result actual = abcRange.accept(context);
		Result expected = new Result(0, 1, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeC() {
		Context context = new Context("c");
		Result actual = abcRange.accept(context);
		Result expected = new Result(0, 1, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeD() {
		Context context = new Context("d");
		Result actual = abcRange.accept(context);
		Result expected = new Result(0, 0, State.FAILURE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeAA() {
		Context context = new Context("aa");
		Result actual = abcRange.accept(context, new Result(0, 1, State.SUCCESS));
		Result expected = new Result(0, 2, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringHello() {
		Context context = new Context("hello");
		Result actual = strAcc.accept(context);
		Result expected = new Result(0, 5, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringHell() {
		Context context = new Context("hell");
		Result actual = strAcc.accept(context);
		Result expected = new Result(0, 4, State.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringBello() {
		Context context = new Context("bello");
		Result actual = strAcc.accept(context);
		Result expected = new Result(0, 0, State.FAILURE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringbbbHello() {
		Context context = new Context("bbbhello");
		Result actual = strAcc.accept(context, new Result(0, 3, State.SUCCESS));
		Result expected = new Result(0, 8, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringbbbHell() {
		Context context = new Context("bbbhell");
		Result actual = strAcc.accept(context, new Result(0, 3, State.SUCCESS));
		Result expected = new Result(0, 7, State.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringbbbBello() {
		Context context = new Context("bbbBello");
		Result actual = strAcc.accept(context, new Result(0, 3, State.SUCCESS));
		Result expected = new Result(0, 3, State.FAILURE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeatcb() {
		Context context = new Context("cb");
		Result actual = repeatAcc.accept(context);
		Result expected = new Result(0, 2, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeatcba() {
		Context context = new Context("cba");
		Result actual = repeatAcc.accept(context);
		Result expected = new Result(0, 3, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeatcbad() {
		Context context = new Context("cbad");
		Result actual = repeatAcc.accept(context);
		Result expected = new Result(0, 3, State.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeatd() {
		Context context = new Context("d");
		Result actual = repeatAcc.accept(context);
		Result expected = new Result(0, 0, State.FAILURE);
		assertEquals(expected, actual);
	}
}
