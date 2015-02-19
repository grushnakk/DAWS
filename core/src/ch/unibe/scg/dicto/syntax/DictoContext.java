package ch.unibe.scg.dicto.syntax;

import java.nio.CharBuffer;

public class DictoContext {

	private final CharBuffer buffer; 
	
	public DictoContext(String text) {
		buffer = CharBuffer.wrap(text); //creates a read-only buffer
	}
	
	/**
	 * returns the subsequence starting and including index start and ending not including index end.
	 * @param start - the starting index of the subsequence. included
	 * @param end - the ending index of the subsequence. not included
	 * @return
	 */
	public CharSequence sequence(int start, int end) {
		return buffer.subSequence(start, end);
	}
	
	public CharSequence sequence(int start) {
		return sequence(start, length());
	}
	
	public int length() {
		return buffer.length();
	}

	@Override
	public String toString() {
		return "DictoContext [length=" + length() + "]";
	}
}
