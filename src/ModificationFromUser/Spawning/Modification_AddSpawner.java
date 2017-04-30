package ModificationFromUser.Spawning;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameEngine.Engine.Spawning.SpawnDataImpl;
/**
 * To Add a spawner to the specified spawnQueue
 * @author Alex
 *
 */
public class Modification_AddSpawner implements ModificationFromUser{

	private String mySpawnQueueName;
	private SpawnDataImpl mySpawnData;
	private boolean isFrequencySpawn;
	
	/**
	 * 
	 * @param spawnQueueName	Name of Queue as a string
	 * @param component			Which component to remove		
	 * @param value				Time of component to remove
	 * @param frequencySpawn 	What list to remove from
	 */
	public Modification_AddSpawner(String spawnQueueName, String component, double value, boolean frequencySpawn) {
		mySpawnQueueName = spawnQueueName;
		mySpawnData = new SpawnDataImpl(component, value);
		isFrequencySpawn = frequencySpawn;
	}
	
	@Override
	public void invoke(Model myModel) throws Exception {
		myModel.getState().getSpawnQueues().get(mySpawnQueueName).add(mySpawnData, isFrequencySpawn);
	}

}
