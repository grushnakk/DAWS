package ch.unibe.scg.dicto.states2;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;

public interface Suggestor {
	
	public static final Suggestor NOTHING = new Suggestor() {
		
		@Override
		public List<String> suggestions(Environment env) {
			return new ArrayList<String>();
		}
	};

	public List<String> suggestions(Environment env);
}
