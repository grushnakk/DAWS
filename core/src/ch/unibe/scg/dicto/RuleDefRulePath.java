package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Predicate;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Acceptor;
import ch.unibe.scg.dicto.parser.AcceptorResult;
import ch.unibe.scg.dicto.parser.MultiStringAcceptor;
import ch.unibe.scg.dicto.states.Next;
import ch.unibe.scg.dicto.states.Path;
import ch.unibe.scg.dicto.states.StateResult;
import static ch.unibe.scg.dicto.Constants.*;
import static ch.unibe.scg.dicto.parser.Acceptors.optionalWhitespace;

public class RuleDefRulePath extends Path {
	
	public RuleDefRulePath(Environment env) {
		super(buildRuleAcceptor(env).chain(optionalWhitespace()));
	}
	
	@Override
	public StateResult onNext(Environment env, AcceptorResult result) {
		return new Next(ID_RULE_ARG); //TODO do some checking and storing
	}
	@Override
	public List<String> suggestions(Environment env) {
		String varNameCache = env.readCache(CACHE_VAR_NAME);
		Variable var = env.getVariable(varNameCache);
		VariableType varType = var.getVariableType();
		Predicate predicate = Predicate.byCode(env.readCache(CACHE_PREDICATE));
		List<String> suggestions = new ArrayList<>();
		for(Rule rule : varType.getRules()) {
			if(rule.canBeUsed(predicate))
				suggestions.add(rule.getName());
		}
		return suggestions;
	}

	private static Acceptor buildRuleAcceptor(Environment env) {
		List<String> rules = new ArrayList<>();
		for(VariableType type : env.getVariableTypes())
			for(Rule rule : type.getRules())
				rules.add(rule.getName());
		return new MultiStringAcceptor(rules).region(REGION_RULE);
	}
}
