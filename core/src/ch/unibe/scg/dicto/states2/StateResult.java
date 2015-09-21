package ch.unibe.scg.dicto.states2;

import ch.unibe.scg.dicto.parser.AcceptorResult;

public class StateResult {
	
	private final boolean error;
	private final String errorMessage;
	private final Path path;
	private final AcceptorResult result;
	
	public StateResult(boolean error, Path path, AcceptorResult result, String errorMessage) {
		this.error = error;
		this.path = path;
		this.result = result;
		this.errorMessage = errorMessage;
	}
	
	public StateResult(String errorMessage) {
		this(true, null, null, errorMessage);
	}
	
	public StateResult(Path path, AcceptorResult result) {
		this(false, path, result, null);
	}
	
	public Path getPath() {
		return path;
	}
	
	public AcceptorResult getAcceptorResult() {
		return result;
	}
	
	public boolean isError() {
		return error;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
