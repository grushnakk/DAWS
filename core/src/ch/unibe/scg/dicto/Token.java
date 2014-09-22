package ch.unibe.scg.dicto;

//TODO: index start end, line, colon
public class Token {
	
	private final TokenType tokenType;
	private final String content;
	
	public Token(TokenType tokenType, String content) {
		this.tokenType = tokenType;
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public TokenType getTokenType() {
		return tokenType;
	}

	@Override
	public String toString() {
		return "Token [tokenType=" + tokenType + ", content=" + content + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tokenType == null) ? 0 : tokenType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (tokenType != other.tokenType)
			return false;
		return true;
	}
	
	
}
