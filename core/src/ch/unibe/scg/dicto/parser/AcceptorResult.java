package ch.unibe.scg.dicto.parser;

import java.util.HashMap;

public class AcceptorResult {
	
	public static enum Type {
		FAILURE, SUCCESS, INCOMPLETE;
	}

	public Type resultType;
	private HashMap<String, String> regions;
	public int begin, end;
	
	public AcceptorResult(int begin, int end, Type state) {
		this.resultType = state;
		this.begin = begin;
		this.end = end;
		this.regions = new HashMap<String, String>();
	}
	
	public Type getResultType() {
		return resultType;
	}

	public AcceptorResult(AcceptorResult result) {
		set(result);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + begin;
		result = prime * result + end;
		result = prime * result + ((regions == null) ? 0 : regions.hashCode());
		result = prime * result + ((resultType == null) ? 0 : resultType.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcceptorResult other = (AcceptorResult) obj;
		if (begin != other.begin)
			return false;
		if (end != other.end)
			return false;
		if (regions == null) {
			if (other.regions != null)
				return false;
		} else if (!regions.equals(other.regions))
			return false;
		if (resultType != other.resultType)
			return false;
		return true;
	}


	public boolean isFailure() {
		return resultType == Type.FAILURE;
	}
	
	public boolean isSuccess() {
		return resultType == Type.SUCCESS;
	}
	
	public boolean isIncomplete() {
		return resultType == Type.INCOMPLETE;
	}
	
	public int size() {
		return end - begin;
	}

	
	@Override
	public String toString() {
		return "Result [state=" + resultType + ", regions=" + regions + ", begin="
				+ begin + ", end=" + end + "]";
	}


	public void set(AcceptorResult result) {
		begin = result.begin;
		end = result.end;
		resultType = result.resultType;
		regions = new HashMap<String, String>(result.regions);
	}
	
	public void addRegion(String key, String value) {
		regions.put(key, value);
	}
	
	public String getRegion(String key) {
		return regions.get(key);
	}
}
