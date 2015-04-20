package ch.unibe.scg.dicto.parser;

public class NegativeRangeAcceptor extends RangeAcceptor {


	public NegativeRangeAcceptor(String range) {
		super(range);
	}
	
	@Override
	protected boolean isInRange(char c) {
		return !super.isInRange(c);
	}
}
