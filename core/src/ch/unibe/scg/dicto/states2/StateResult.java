package ch.unibe.scg.dicto.states2;

import ch.unibe.scg.dicto.parser.AcceptorResult;

public class StateResult {
	
	private final boolean failure;
	private final Path path;
	private final AcceptorResult result;
	
	public StateResult(boolean failure, Path path, AcceptorResult result) {
		this.failure = failure;
		this.path = path;
		this.result = result;
	}
	
	public Path getPath() {
		return path;
	}
	
	public AcceptorResult getAcceptorResult() {
		return result;
	}
	
	public boolean isFailure() {
		return failure;
	}
}
