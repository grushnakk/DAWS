package ch.unibe.scg.dicto.parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.Context;
import ch.unibe.scg.dicto.parser.Acceptor;

public class AcceptorTest {

	private Acceptor acceptor;
	private Acceptor abcRange;
	private Acceptor strAcc;
	private Acceptor repeatAcc;
	
	@Before
	public void setUp() {
		acceptor = new ChainAcceptor(new RepeatAcceptor(new RangeAcceptor("abcdefghijklmnopqrstuvwxyz")), 
				new OptionalAcceptor(new RepeatAcceptor(new RangeAcceptor(" \t\r\n"))), 
				new StringAcceptor(":"));
		abcRange = new RangeAcceptor("abc");
		strAcc = new StringAcceptor("hello");
		repeatAcc = new RepeatAcceptor(abcRange);
	}
	
	@Test
	public void full() {
		String input = "hello :";
		int actual = acceptor.accept(new Context(input), 0);
		int expected = 7;
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeA() {
		Context context = new Context("a");
		int actual = abcRange.accept(context, 0);
		int expected = 1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeC() {
		Context context = new Context("c");
		int actual = abcRange.accept(context, 0);
		int expected = 1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeD() {
		Context context = new Context("d");
		int actual = abcRange.accept(context, 0);
		int expected = -1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void rangeAA() {
		Context context = new Context("aa");
		int actual = abcRange.accept(context, 1);
		int expected = 1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringHello() {
		Context context = new Context("hello");
		int actual = strAcc.accept(context, 0);
		int expected = 5;
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringHell() {
		Context context = new Context("hell");
		int actual = strAcc.accept(context, 0);
		int expected = 4;
		assertEquals(expected, actual);
	}
	
	@Test
	public void stringBello() {
		Context context = new Context("bello");
		int actual = strAcc.accept(context, 0);
		int expected = -1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeatcb() {
		Context context = new Context("cb");
		int actual = repeatAcc.accept(context, 0);
		int expected = 2;
		assertEquals(expected, actual);
	}
	
	@Test
	public void repeatcba() {
		Context context = new Context("cba");
		int actual = repeatAcc.accept(context, 0);
		int expected = 3;
		assertEquals(expected, actual);
	}
}
