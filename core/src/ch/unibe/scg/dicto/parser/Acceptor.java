package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.Context;
import ch.unibe.scg.dicto.parser.Result.State;

public abstract class Acceptor {

	public abstract Result accept(Context context, Result result);
	
	public Result accept(Context context) {
		return accept(context, new Result(context.getCurrentIndex(), context.getCurrentIndex(), State.SUCCESS));
	}
}
