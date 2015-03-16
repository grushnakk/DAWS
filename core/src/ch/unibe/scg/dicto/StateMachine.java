package ch.unibe.scg.dicto;

public class StateMachine implements Runnable {

	private State current;
	private final Context context;
	private final Environment environment;
	
	public StateMachine(State start, Context context, Environment environment) {
		this.current = start;
		this.context = context;
		this.environment = environment;
	}
	
	public void setState(State state) {
		this.current = state;
	}
	
	public Context getContext() {
		return context;
	}
	
	public Environment getEnvironment() {
		return environment;
	}
	
	public State getCurrentState() {
		return current;
	}
	
	public void run() {
		while(context.sizeLeft() >= 0)
			current.process(this);
	}
}
