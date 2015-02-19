package ch.unibe.scg.dicto.syntax;

public class Token {

	private final int start, end;
	private final DictoContext context;
	private final TokenType type;
	
	public Token(int start, int end, DictoContext context, TokenType type) {
		this.start = start;
		this.end = end;
		this.context = context;
		this.type = type;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public DictoContext getContext() {
		return context;
	}
	
	public CharSequence getContent() {
		return context.sequence(start, end);
	}

	@Override
	public String toString() {
		return "Token [start=" + start + ", end=" + end + ", type=" + type + "]";
	}
}
