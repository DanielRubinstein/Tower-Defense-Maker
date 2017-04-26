package ModificationFromUser.Spawning;

import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;

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
		System.out.println(this.getClass().getSimpleName() + ": " + spawnQueueName);
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		State state = myModel.getState();
		Map<String, SpawnQueue> spawnQueues = state.getSpawnQueues();
		if (!spawnQueues.containsKey(mySpawnQueueName)) {
			System.out.println(this.getClass().getSimpleName() + ": " + "New spawn queue");
			spawnQueues.put(mySpawnQueueName, new SpawnQueue());
		} else {
			System.out.println("ERROR: NON-UNIQUE SPAWN QUEUE ID");
		}
	}

}
