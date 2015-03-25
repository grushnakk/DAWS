package ch.unibe.scg.dicto.parser;


/**
 * this is a wrapper class for the input. it provides method aiding the position of the current state.
 * @author Kenneth
 *
 */
public class Context {

	private final String content;
	private int index;
	
	public Context(String content) {
		this.content = content;
		this.index = 0;
	}
	
	public char currentChar() {
		return content.charAt(index);
	}
	
	public char charAt(int offset) {
		return content.charAt(index + offset);
	}
	
	public int size() {
		return content.length();
	}
	
	public int sizeLeft() {
		return content.length() - index;
	}
	
	public int getCurrentIndex() {
		return index;
	}
	
	public String substring(int length) {
		return content.substring(index, index + length);
	}
	
	public String substring(int start, int end) {
		return content.substring(start, end);
	}
	
	public void incrementIndex(int amount) {
		if(amount <= 0)
			throw new IllegalArgumentException("amount needs to be greater than zero, but was" + amount);
		index += amount;
	}
	
	public void incrementIndex() {
		index++;
	}
	
	public void apply(AcceptorResult result) {
		incrementIndex(result.size());
	}
}
