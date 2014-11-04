package ch.unibe.scg.dicto;

import java.util.ArrayList;
import java.util.List;

/*not thread safe*/
public final class Configuration {

	private static Configuration INSTANCE;
	
	public static Configuration getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Configuration();
		return INSTANCE;
	}
	
	private final List<VariableType> variableTypes;
	private VariableTypeGrammar variableTypeGrammar;
	private boolean open;
	
	private Configuration() {
		open = true;
		variableTypeGrammar = null;
		variableTypes = new ArrayList<>();
	}
	
	public List<VariableType> getVariableTypes() {
		if(open) throw new ConfigurationException("configuration is still open");
		return variableTypes;
	}
	
	public void addVariableType(VariableType type) {
		if(!open) throw new ConfigurationException("tried to modify the closed Configuration");
		if(type == null) throw new IllegalArgumentException("type should not be null");
		variableTypes.add(type);
	}
	
	public void close() {
		open = false;
	}
	
	public VariableTypeGrammar getVariableTypeGrammar() {
		if(open) throw new ConfigurationException("configuration is still open");
		return variableTypeGrammar;
	}
	
	public void setVariableTypeGrammar(VariableTypeGrammar variableTypeGrammar) {
		if(!open) throw new ConfigurationException("tried to modify the closed Configuration");
		if(variableTypeGrammar == null) throw new IllegalArgumentException("variableTypeGrammar should not be null");
		this.variableTypeGrammar = variableTypeGrammar;
	}
}
