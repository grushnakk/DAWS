package ch.unibe.scg.dicto.states2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.Context;

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

	@Override
	public StateResult accept(Context context) {
		for(Path path : paths) {
			AcceptorResult accResult = path.getAcceptor().accept(context);
			if(!accResult.isFailure()) {
				context.apply(accResult);
				return new StateResult(false, path, accResult);
			}
		}
		return new StateResult(true, null, null);
	}
	
	@Override
	public List<String> suggestions(Environment env) {
		List<String> merged = new ArrayList<String>();
		for(Path path : paths) {
			
			merged.addAll(path.getSuggestor().suggestions(env));
		}
		return merged;
	}
}
