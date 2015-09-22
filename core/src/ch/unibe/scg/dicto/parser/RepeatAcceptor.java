package ch.unibe.scg.dicto.parser;

public class RepeatAcceptor extends Acceptor {

	private final Acceptor baseAcceptor;
	
	public RepeatAcceptor(Acceptor baseAcceptor) {
		this.baseAcceptor = baseAcceptor;
	}
	
	@Override
	public AcceptorResult accept(Context context, final AcceptorResult result) {
//		if(!result.isSuccess()) return result;
//		AcceptorResult copy = null;
//		AcceptorResult save = new AcceptorResult(result);
//		boolean accOnce = false;
//		do {
//			copy = new AcceptorResult(save);
//			baseAcceptor.accept(context, copy);
//			if(!copy.isFailure()) {
//				save = copy;
//				accOnce = true;
//			}
//		} while(save.end < context.size() && copy.isSuccess());
//		result.set(save);
//		if(!result.isSuccess() && accOnce)
//			result.resultType = Type.SUCCESS;
//		return result;
		if(!result.isSuccess()) return result;
		//accept the first time
		AcceptorResult copy = new AcceptorResult(result);
		copy = baseAcceptor.accept(context, copy);
		if(!copy.isSuccess())
			return copy;
		AcceptorResult old = null;
		AcceptorResult neu = copy;
		while(neu.end < context.size() && neu.isSuccess()) {
			old = neu;
			neu = baseAcceptor.accept(context, new AcceptorResult(old));
		}
		if(neu.isFailure())
			return old;
		return neu;
	}
	
	@Override
	public Acceptor repeat() {
		return this;
	}
}
