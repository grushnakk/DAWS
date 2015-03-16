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
	public int accept(Context context, final int offset) {
		int length = 0;
		for(Acceptor acceptor : acceptors) {
			int result = acceptor.accept(context, offset + length);
			if(result == FAILURE)
				return FAILURE;
			length += result;
		}
		return length;
	}

}
