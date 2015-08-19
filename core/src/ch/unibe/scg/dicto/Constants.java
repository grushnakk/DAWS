package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_DIGITS;
import static ch.unibe.scg.dicto.parser.Acceptors.RANGE_LETTERS;
import static ch.unibe.scg.dicto.parser.Acceptors.range;

import ch.unibe.scg.dicto.parser.Acceptor;

class Constants {
	
	/**
	 * defines how variables in DICTO may look like.
	 */
	static final Acceptor ID_ACC = range(RANGE_DIGITS + RANGE_LETTERS).repeat();
	
	static final String REGION_IDENTIFIER = "ID";
	static final String REGION_STRING_CONTENT = "SC";
	static final String REGION_PREDICATE = "P";
	static final String REGION_RULE = "R";

	static final String CACHE_VAR_NAME = "VAR_NAME";
	static final String CACHE_VAR_TYPE = "VAR_TYPE";
	static final String CACHE_ARG_NAME = "ARG_NAME";
	static final String CACHE_PREDICATE = "PREDICATE";
	
	static final int ID_START = 00;
	static final int ID_TYPE = 11;
	static final int ID_KEYWORD_WITH = 12;
	static final int ID_ARG_NAME = 13;
	static final int ID_ARG_VALUE = 14;
	static final int ID_AFTER_ARG_VALUE = 15;
	static final int ID_PREDICATE = 21;
	static final int ID_RULE = 22;
	static final int ID_RULE_ARG = 23;
}

