package ModificationFromUser.Spawning;

import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;

/**
 * Adds a spawn queue of a given name
 * @author Alex
 *
 */
public class Modification_AddSpawnQueue implements ModificationFromUser {

	private String mySpawnQueueName;
	
	/**
	 * 
	 * @param spawnQueueName	Name of Queue as a string
	 * @param component			Which component to remove		
	 * @param time				Time of component to remove
	 * @param frequencySpawn 	What list to remove from
	 */
	public Modification_AddSpawnQueue(String spawnQueueName) {
		mySpawnQueueName = spawnQueueName;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		Map<String, SpawnQueues> spawnQueues = myModel.getState().getSpawnQueues();
		if (!spawnQueues.containsKey(mySpawnQueueName)) {
			spawnQueues.put(mySpawnQueueName, new SpawnQueues());
		} else {
			throw new Exception("Non-unique spawn timeline name");
		}
	}

}
