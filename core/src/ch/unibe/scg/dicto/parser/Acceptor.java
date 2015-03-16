package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.Context;

public abstract class Acceptor {

	public static final int FAILURE = -1;

	public abstract int accept(Context context, int offset);
	
	public int accept(Context context) {
		return accept(context, 0);
	}
}
