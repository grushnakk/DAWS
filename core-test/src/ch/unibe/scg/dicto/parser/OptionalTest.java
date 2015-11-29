package ch.unibe.scg.dicto.parser;

import static org.junit.Assert.assertEquals;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import ch.unibe.scg.dicto.parser.AcceptorResult.Type;

public class OptionalTest {

	Acceptor acc;
	
	@Before
	public void setUp() {
		acc = new OptionalAcceptor(new StringAcceptor("with"));
	}
	
	@Test
	public void complete() {
		AcceptorResult actual = acc.accept(new Context("with"));
		AcceptorResult expected = new AcceptorResult(0, 4, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void incomplete() {
		AcceptorResult actual = acc.accept(new Context("wi"));
		AcceptorResult expected = new AcceptorResult(0, 2, Type.INCOMPLETE);
		assertEquals(expected, actual);
	}
	
	@Test
	public void nothing() {
		AcceptorResult actual = acc.accept(new Context(""));
		AcceptorResult expected = new AcceptorResult(0, 0, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void nothing2() {
		AcceptorResult actual = acc.accept(new Context("banana"));
		AcceptorResult expected = new AcceptorResult(0, 0, Type.SUCCESS);
		assertEquals(expected, actual);
	}
	
	@Test
	public void java() {
		Pattern p = Pattern.compile("(wi)?(wi)");
		System.out.println(p.matcher("wi").matches());
	}
}
