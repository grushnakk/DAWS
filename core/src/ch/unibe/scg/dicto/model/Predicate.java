package ch.unibe.scg.dicto.model;
//must, can, cannot, can only, only _ can
public enum Predicate {
	MUST("must"), CAN("can"), CANNOT("can not"), CAN_ONLY("can only"), ONLY_CAN("only can");
	
	private final String code;
	Predicate(String code) {
		this.code = code;
	}
	
	public static Predicate byCode(String code) {
		for(Predicate predicate : values())
			if(predicate.code.equals(code))
				return predicate;
		return null;
	}
}
