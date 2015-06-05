package ch.unibe.scg.dicto.parser;

public final class Acceptors {

	public static final String RANGE_UC_A_Z = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String RANGE_LC_A_Z = "abcdefghijklmnopqrstuvwxyz";
	public static final String RANGE_LETTERS = RANGE_UC_A_Z + RANGE_LC_A_Z;
	public static final String RANGE_DIGITS = "0123456789";
	public static final String RANGE_HOR_WHITESPACE = " \t";
	public static final String RANGE_VER_WHITESPACE = "\r\n";
	public static final String RANGE_WHITESPACE = RANGE_HOR_WHITESPACE + RANGE_VER_WHITESPACE;

	
	
	public static Acceptor range(String range) {
		return new RangeAcceptor(range);
	}
	
	public static Acceptor negRange(String range) {
		return new NegativeRangeAcceptor(range);
	}
	
	public static Acceptor string(String match) {
		return new StringAcceptor(match);
	}
	
	public static Acceptor optionalWhitespace() {
		return whitespace().optional();
	}
	
	public static Acceptor whitespace() {
		return range(RANGE_HOR_WHITESPACE).repeat();
	}
}
