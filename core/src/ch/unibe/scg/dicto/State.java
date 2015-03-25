package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.parser.AcceptorResult;

public class State {
	
	public List<Path> paths;
	
	public State(Path... paths) {
		this.paths = new ArrayList<>();
		for(Path a : paths)
			this.paths.add(a);
	}
	
	public void process(StateMachine stateMachine) {
		Context context = stateMachine.getContext();
		AcceptorResult res = null;
		for(Path path : paths) {
			res = path.accept(context);
			switch(res.state) {
			case FAILURE:
				break;
			case INCOMPLETE:
				context.incrementIndex(res.size());
				return;
			case SUCCESS:
				context.incrementIndex(res.size());
				path.onSuccess(stateMachine, res);
				break;
			default:
				break;
			}
		}
	}
}
