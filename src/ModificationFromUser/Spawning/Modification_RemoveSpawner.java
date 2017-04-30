package ModificationFromUser.Spawning;


import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameEngine.Engine.Spawning.SpawnDataReader;

/**
 * Modification to remove spawner from a list
 * @author Alex
 *
 */
public class Modification_RemoveSpawner implements ModificationFromUser{

	private String mySpawnQueueName;
	private SpawnDataReader mySpawnDataToRemove;
	/**
	 * 
	 * @param spawnQueueName	Name of Queue as a string
	 * @param component			Which component to remove		
	 * @param time				Time of component to remove
	 * @param frequencySpawn 	What list to remove from
	 */
	public Modification_RemoveSpawner(String spawnQueueName, SpawnDataReader spawnDataToRemove) {
		mySpawnQueueName = spawnQueueName;
		mySpawnDataToRemove = spawnDataToRemove;
	}
	
	@Override
	public void invoke(Model myModel) throws Exception {
		myModel.getState().getSpawnQueues().get(mySpawnQueueName).remove(mySpawnDataToRemove);
	}
}
