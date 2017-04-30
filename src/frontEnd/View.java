package frontEnd;

import java.util.Collection;
import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.GameData.Rules.RuleReader;
import backEnd.GameData.State.Component;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
import backEnd.LevelProgression.LevelProgressionControllerEditor;

/**
 * Interface that defines how the Front End should be controlled and how the
 * Front End and Model interact.
 * 
 * @author Tim, Miguel
 *
 */
public interface View extends ViewReader {

	/**
	 * Sends this invokable and ensures that it performs its task. This is how
	 * the front end interacts and affects the back end.
	 * 
	 * @param mod
	 */
	public void sendUserModification(ModificationFromUser mod);

	/**
	 * Gets the SpawnQueues that exist in the back end. These SpawnQueues define
	 * which components spawn and when.
	 * 
	 * @return Map from String of the name of the SpawnQueue to the actual
	 *         SpawnQueue
	 */
	public Map<String, SpawnQueues> getSpawnQueues();

	/**
	 * Gets the LevelProgressionController, which controls all levels and their
	 * order.
	 * 
	 * @return
	 */
	public LevelProgressionControllerEditor getLevelProgressionController();

	/**
	 * 
	 * @return The rules of the game in a Collection
	 */
	public Collection<RuleReader> getRules();

	/**
	 * Check to see whether an individual component is currently on the grid.
	 * This is so that certain options are only pulled up when the Command
	 * center is created
	 * 
	 * @param c
	 * @return
	 */
	public boolean isComponentOnGrid(Component c);

}
