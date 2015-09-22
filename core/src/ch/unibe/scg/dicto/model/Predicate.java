package ch.unibe.scg.dicto.model;

public enum Predicate {
	//REMINDER ORDER IS IMPORTANT
	MUST("must"), CAN_ONLY("can only"), CANNOT("cannot"), CAN("can"), ONLY_CAN("only can");
	
	private final String code;
	
	Predicate(String code) {
		this.code = code;
	}
	
	public String getCode() { //TODO rename
		return code;
	}
	
	public static Predicate byCode(String code) {
		for(Predicate predicate : values())
			if(predicate.code.equals(code))
				return predicate;
		return null;
	}
}
