package ch.unibe.scg.dicto.states;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.Context;

public class State {
	
	public List<Path> paths;
	
	public State(Path... paths) {
		this.paths = new ArrayList<>();
		for(Path a : paths)
			this.paths.add(a);
	}
	
	public StateResult process(Environment env, Context context) {
		AcceptorResult res = null;
		for(Path path : paths) {
			res = path.accept(context);
			switch(res.state) {
			case FAILURE:
				break;
			case INCOMPLETE:
				context.incrementIndex(res.size());
				return StateResult.incomplete();
			case SUCCESS:
				context.incrementIndex(res.size());
				return path.onSuccess(env, res);
			}
		}
		return StateResult.failure();
	}
}
