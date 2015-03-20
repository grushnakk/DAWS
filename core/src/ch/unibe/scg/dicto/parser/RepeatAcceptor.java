package ch.unibe.scg.dicto.parser;


import ch.unibe.scg.dicto.Context;
import ch.unibe.scg.dicto.parser.Result.State;

public class RepeatAcceptor extends Acceptor {

	private final Acceptor baseAcceptor;
	
	public RepeatAcceptor(Acceptor baseAcceptor) {
		this.baseAcceptor = baseAcceptor;
	}
	
	@Override
	public Result accept(Context context, final Result result) {
		if(result.isFailure()) return result;
		Result copy = null;
		Result save = new Result(result);
		boolean accOnce = false; //XXX is this ugly
		do {
			copy = new Result(save);
			baseAcceptor.accept(context, copy);
			if(!copy.isFailure()) {
				save = copy;
				accOnce = true;
			}
		} while(!copy.isFailure() && context.size() > save.end);
		result.set(save);
		if(!accOnce)
			result.state = State.FAILURE;
		return result;
	}
}
