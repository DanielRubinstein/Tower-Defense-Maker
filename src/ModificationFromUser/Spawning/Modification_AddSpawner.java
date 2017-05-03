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
	
	/**
	 * 
	 * @param spawnQueueName	Name of Queue as a string
	 * @param component			Which component to remove		
	 * @param value				Time of component to remove
	 */
	public Modification_AddSpawner(String spawnQueueName, String component, double frequency, double delay, int spawns) {
		mySpawnQueueName = spawnQueueName;
		mySpawnData = new SpawnDataImpl(component, frequency, delay, spawns);
	}
	
	@Override
	public void invoke(Model myModel) throws Exception {
		myModel.getState().getSpawnQueues().get(mySpawnQueueName).add(mySpawnData);
	}

}
