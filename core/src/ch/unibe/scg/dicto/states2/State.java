package ch.unibe.scg.dicto.states2;

import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.Context;

public interface State {

	public String getName();
	
	public List<Path> getPaths();
	
	public StateResult accept(Context context);
	
	public List<String> suggestions(Environment env);
}
