package ModificationFromUser.Spawning;

import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;

/**
 * Removes a spawn queue
 * @author Alex
 *
 */
public class Modification_RemoveSpawnQueue implements ModificationFromUser{

	private String mySpawnQueueName;
	
	/**
	 * Removes a spawn queue by name
	 * @param spawnQueueName
	 */
	public Modification_RemoveSpawnQueue(String spawnQueueName) {
		mySpawnQueueName = spawnQueueName;
	}
	
	@Override
	public void invoke(Model myModel) throws Exception {
		State state = myModel.getState();
		Map<String, SpawnQueues> spawnQueues = state.getSpawnQueues();
		if (spawnQueues.containsKey(mySpawnQueueName)) {
			spawnQueues.remove(mySpawnQueueName);
		} else {
			System.out.println("ERROR: SPAWN QUEUE ID NOT THERE TO REMOVE");
		}
	}

}
