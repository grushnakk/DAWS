package ch.unibe.scg.dicto.states;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.Context;

// the last success binds
public class State {

	private final List<Path> paths;
	private final int id;
	
	public State(int id, List<Path> paths) {
		this.id = id;
		this.paths = new ArrayList<>(paths);
	}
	
	public State(int id, Path... paths) {
		this.id = id;
		this.paths = new ArrayList<>();
		for(Path path : paths) {
			this.paths.add(path);
		}
	}
	
	public StateResult process(Context context, Environment env) { //TODO better name
		AcceptorResult acceptorResult = null;
		for(Path path : paths) {
			acceptorResult = path.accept(context);
			switch(acceptorResult.resultType) {
			case FAILURE:
				break;
			case INCOMPLETE:
				context.apply(acceptorResult);
				return success(env);
			case SUCCESS:
				context.apply(acceptorResult);
				return path.onNext(env, acceptorResult);
			}
		}
		return error(context);
	}
	
	private StateResult success(Environment env) {
		return new Success(suggestions(env));
	}
	
	private StateResult error(Context context) {
		return new LangError("failed at state " + id); //TODO implement
	}
	
	public List<String> suggestions(Environment env) {
		List<String> merged = new ArrayList<>();
		for(Path path : paths) {
			merged.addAll(path.suggestions(env));
		}
		return merged;
	}
	
}
