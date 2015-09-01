package ch.unibe.scg.dicto.states2;

import java.util.ArrayList;
import java.util.List;

public class StateRef implements State {
	
	private static final List<Path> EMPTY_LIST = new ArrayList<Path>();
	
	private final String name;
	
	public StateRef(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Path> getPaths() {
		return EMPTY_LIST;
	}

}
