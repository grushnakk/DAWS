package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.Constants.*;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.states.State;
import ch.unibe.scg.dicto.states.StateMachine;

public class DictoBuilder {

	private Environment env;

	public DictoBuilder(Environment env) {
		setEnvironment(env);
	}

	public void setEnvironment(Environment env) {
		this.env = env;
	}

	public Environment getEnvironment() {
		return env;
	}

	public StateMachine build() {
		StateMachine dictoMachine = new StateMachine(ID_START);
		dictoMachine.addState(ID_START, new State(ID_START, new RuleOnlyPath(), new VarDefStartPath(), new RuleDefStartPath()));
		dictoMachine.addState(ID_TYPE, new State(ID_TYPE, new VarDefTypePath(env)));
		dictoMachine.addState(ID_KEYWORD_WITH, new State(ID_KEYWORD_WITH, new KeywordPath("with", ID_ARG_NAME)));
		dictoMachine.addState(ID_ARG_NAME, new State(ID_ARG_NAME, new VarDefArgNamePath()));
		dictoMachine.addState(ID_ARG_VALUE, new State(ID_ARG_VALUE, new VarDefArgStringPath()));
		dictoMachine.addState(ID_AFTER_ARG_VALUE, new State(ID_AFTER_ARG_VALUE, new VarDefNextArgPath(), new VarDefNextStatementPath()));
		dictoMachine.addState(ID_PREDICATE, new State(ID_PREDICATE, new RuleDefPredicatePath()));
		dictoMachine.addState(ID_RULE, new State(ID_RULE, new RuleDefRulePath(env)));
		dictoMachine.addState(ID_RULE_ARG, new State(ID_RULE_ARG, new RuleDefArgStringPath(), new RuleDefArgVariablePath()));
		dictoMachine.addState(ID_RULE_AFTER_ARG, new State(ID_RULE_AFTER_ARG, new RuleDefNextArgPath(), new RuleDefNextStatementPath()));
		dictoMachine.addState(ID_RULE_ONLY_VAR, new State(ID_RULE_ONLY_VAR, new RuleDefStartPath()));
		return dictoMachine;
	}
}
