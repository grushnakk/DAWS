package ch.unibe.scg.dicto.parser;

import ch.unibe.scg.dicto.Context;

public class RegionAcceptor extends Acceptor {

	private final String key;
	private final Acceptor baseAcceptor;
	
	public RegionAcceptor(String key, Acceptor baseAcceptor) {
		this.key = key;
		this.baseAcceptor = baseAcceptor;
	}
	
	@Override
	public Result accept(Context context, Result result) {
		if(result.isFailure()) return result;
		int temp = result.end;
		baseAcceptor.accept(context, result);
		if(!result.isFailure())
			result.addRegion(key, context.substring(temp, result.end));
		return result;
	}

}
