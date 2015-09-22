package ch.unibe.scg.dicto.parser;


public class RegionAcceptor extends Acceptor {

	private final String key;
	private final Acceptor baseAcceptor;
	
	public RegionAcceptor(String key, Acceptor baseAcceptor) {
		this.key = key;
		this.baseAcceptor = baseAcceptor;
	}
	
	@Override
	public AcceptorResult accept(Context context, final AcceptorResult result) {
		if(result.isFailure()) return result;
		int temp = result.end;
		AcceptorResult newResult = baseAcceptor.accept(context, result);
		if(!newResult.isFailure())
			newResult.addRegion(key, context.substring(temp, newResult.end));
		return newResult;
	}

}
