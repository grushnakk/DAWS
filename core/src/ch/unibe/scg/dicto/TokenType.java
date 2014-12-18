package ch.unibe.scg.dicto;

import org.petitparser.parser.Parser;


public class TokenType {

	protected final String name;
	protected final Parser parser;
	
	public TokenType(String name, Parser parser) {
		this.name = name;
		this.parser = parser;
	}

	/**
	 * creates a TokenType without parser. It should be used for Type checking.
	 * @param name
	 */
	public TokenType(String name) {
		this.name = name;
		this.parser = null;
	}
	
	public String getName() {
		return name;
	}
	
	public Parser getParser() {
		return parser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TokenType other = (TokenType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TokenType [name=" + name + "]";
	}
	
}
