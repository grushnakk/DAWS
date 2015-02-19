package ch.unibe.scg.dicto.syntax;

import org.petitparser.Parsers;
import org.petitparser.context.Context;
import org.petitparser.context.Result;
import org.petitparser.parser.Parser;

public class Playground {

	public static void main(String[] args) {
		Context context = new Context("hello world!");
		Parser parser = Parsers.string("hello");
		Result result = parser.parse(context);
		System.out.println(result.getPosition());
	}
}
