package ch.unibe.scg.dicto.states;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.unibe.scg.dicto.Token;

public class StateMachine {

	protected Map<String, Object> cache;
	protected final State first;
	protected State next;
	
	public StateMachine(State first) {
		this.first = first;
	}
	
	public void consume(List<Token> tokens) throws UnexpectedTokenException {
		cache = new HashMap<String, Object>();
		next = first;
		Token token;
		for(int i = 0; i < tokens.size(); i++) {
			token = tokens.get(i);
			if(next == null)
				throw new UnsupportedOperationException("unexpected token exception: " + tokens.get(i - 1)); //FIXME better error handling pls
			next = next.consume(token, this);
		}
	}
	
	/**
	 * puts a value into the cache
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		cache.put(key, value);
	}
	
	/**
	 * retrieves a value from the cache
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return cache.get(key);
	}
}
