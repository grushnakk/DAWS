package ch.unibe.scg.dicto.states2;

import java.util.ArrayList;
import java.util.List;

public class StateBuilder {
	
	private String name;
	private List<Path> paths;
	private final StateMachineBuilder parent;

	StateBuilder(StateMachineBuilder parent) {
		this.parent = parent;
		paths = new ArrayList<Path>();
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public void addPath(Path path) {
		paths.add(path);
	}
	
	public PathBuilder pathTo(String destinationName) {
		return new PathBuilder(this).to(parent.ref(destinationName));
	}
	
	State build() {
		return new StateImpl(name, paths);
	}
}
