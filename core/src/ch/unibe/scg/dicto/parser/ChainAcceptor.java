package ch.unibe.scg.dicto.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ChainAcceptor extends Acceptor {
	
	private final List<Acceptor> acceptors;
	
	public ChainAcceptor(List<Acceptor> acceptors) {
		this.acceptors = Collections.unmodifiableList(acceptors);
	}
	
	public ChainAcceptor(Acceptor... acceptors) {
		this.acceptors = new ArrayList<Acceptor>();
		for(Acceptor a : acceptors) {
			this.acceptors.add(a);
		}
	}

	@Override
	public AcceptorResult accept(Context context, AcceptorResult result) {
		for(Acceptor acceptor : acceptors) {
			result = acceptor.accept(context, result);
			if(!result.isSuccess())
				return result;
			
		}
		return result;
	}

	@Override
	public Acceptor chain(Acceptor... acceptors) {
		for(Acceptor acc : acceptors)
			this.acceptors.add(acc);
		return this;
	}
}
