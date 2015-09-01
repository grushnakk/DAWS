package ch.unibe.scg.dicto.states2;

import java.util.Collections;
import java.util.List;

//TODO naming - help me :(
class StateImpl implements State {
	
	private final String name;
	private final List<Path> paths;
	
	StateImpl(String name, List<Path> paths) {
		this.name = name;
		this.paths = Collections.unmodifiableList(paths);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Path> getPaths() {
		return paths;
	}
}
