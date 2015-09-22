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
	public StateResult accept(Context context, Environment env) {
		for(Path path : paths) {
			AcceptorResult accResult = path.getAcceptor().accept(context);
			switch(accResult.getResultType()) {
			case FAILURE:
				break;
			case INCOMPLETE:
				context.apply(accResult);
				return new StateResult(path, accResult);
			case SUCCESS:
				context.apply(accResult);
				return path.getAfterSuccessAction().apply(new StateResult(path, accResult), env);
			}
		}
		return new StateResult(true, null, null, "unexpected token at: " + context.getCurrentIndex()+ " in state " + name);
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
