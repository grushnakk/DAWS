package ch.unibe.scg.dicto.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.unibe.scg.dicto.Context;


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
	public Result accept(Context context, Result result) {
		for(Acceptor acceptor : acceptors) {
			acceptor.accept(context, result);
			System.out.println(result);
			if(!result.isSuccess())
				return result;
		}
		return result;
	}

}
