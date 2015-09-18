package ch.unibe.scg.dicto.states2;

import java.util.List;

public interface State {

	public String getName();
	
	public List<Path> getPaths();
}
