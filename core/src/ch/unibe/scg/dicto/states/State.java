package ch.unibe.scg.dicto.states;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.Context;

// the last success binds
public class State {

	private final List<Path> paths;
	
	public State(List<Path> paths) {
		this.paths = new ArrayList<>(paths);
	}
	
	public State(Path... paths) {
		this.paths = new ArrayList<>();
		for(Path path : paths) {
			this.paths.add(path);
		}
	}
	
	public StateResult process(Context context, Environment env) { //TODO better name
		StateResult lastSuccess = null;
		AcceptorResult lastSuccessAcceptor = null;
		AcceptorResult acceptorResult = null;
		for(Path path : paths) {
			acceptorResult = path.accept(context);
			switch(acceptorResult.state) {
			case FAILURE:
				break;
			case INCOMPLETE:
				context.apply(acceptorResult);
				return success(env);
			case SUCCESS:
				lastSuccess = path.onNext(env, acceptorResult);
				lastSuccessAcceptor = acceptorResult;
				break;
			}
		}
		if(lastSuccess != null) {
			context.apply(lastSuccessAcceptor);
			return lastSuccess;
		}
		return error(context);
	}
	
	private StateResult success(Environment env) {
		throw new UnsupportedOperationException("not supported yet."); //TODO implement
	}
	
	private StateResult error(Context context) {
		throw new UnsupportedOperationException("not supported yet."); //TODO implement
	}
}
