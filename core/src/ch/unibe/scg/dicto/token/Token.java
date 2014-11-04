package ch.unibe.scg.dicto.token;

public interface Token {
	
	public void applyToTokenizer(Tokenizer tokenizer);
	
	public String getName();
	
	public String getContent();
}
