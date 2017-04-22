package backEnd.GameEngine.Engine;

import backEnd.GameData.GameData;

public interface Engine{
	
	/**
	 * takes in the currently active State from Environment and modifies it based
	 * on the State's Attributes and Behavior.
	 * @param myGameData the current state of the Environment
	 * @param stepTime 
	 */
	public void gameLoop(GameData myGameData, double stepTime);
}
