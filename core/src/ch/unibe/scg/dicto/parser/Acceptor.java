package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.parser.AcceptorResult.Type;

public abstract class Acceptor {

	public abstract AcceptorResult accept(Context context, AcceptorResult result);
	
	public AcceptorResult accept(Context context) {
		return accept(context, new AcceptorResult(context.getCurrentIndex(), context.getCurrentIndex(), Type.SUCCESS));
	}
	
	public Acceptor repeat() {
		return new RepeatAcceptor(this);
	}
	
	public Acceptor region(String key) {
		return new RegionAcceptor(key, this);
	}
	
	public Acceptor optional() {
		return new OptionalAcceptor(this);
	}
	
	public Acceptor chain(Acceptor... acceptors) {
		Acceptor[] arr = new Acceptor[acceptors.length + 1];
		arr[0] = this;
		for(int i = 0; i < acceptors.length; i++)
			arr[i + 1] = acceptors[i];
		return new ChainAcceptor(arr);
	}
}
