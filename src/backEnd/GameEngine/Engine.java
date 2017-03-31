package backEnd.GameEngine;
import backEnd.State;

public interface Engine {
	
	/**
	 * takes in the currently active State from Environment and modifies it based
	 * on the State's Attributes and Behavior.
	 * @param currentState the current state of the Environment
	 */
	public void gameLoop(State currentState);
}
