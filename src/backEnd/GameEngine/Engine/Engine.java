package backEnd.GameEngine.Engine;
import java.util.Observer;

import backEnd.GameData.State.State;

public interface Engine{
	
	/**
	 * takes in the currently active State from Environment and modifies it based
	 * on the State's Attributes and Behavior.
	 * @param currentState the current state of the Environment
	 * @param stepTime 
	 */
	public void gameLoop(State currentState, double stepTime);
}
