package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.parser.Acceptors.string;
import static ch.unibe.scg.dicto.parser.Acceptors.whitespace;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;

class KeywordPath extends Path {

	private final String keyword;
	private final int nextId;
	
	public KeywordPath(String keyword, int nextId) {
		super(string(keyword).chain(whitespace()));
		this.keyword = keyword;
		this.nextId = nextId;
	}

	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		return new Next(nextId);
	}

	@SuppressWarnings("serial")
	@Override
	public List<String> suggestions(Environment env) {
		return new ArrayList<String>(){{add(keyword);}};
	}

}
