package ModificationFromUser.Spawning;

import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.Spawning.SpawnData;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;
/**
 * To Add a spawner to the specified spawnQueue
 * @author Alex
 *
 */
public class Modification_AddSpawner implements ModificationFromUser{

	private String mySpawnQueue;
	private SpawnData mySpawnData;
	private boolean isFrequencySpawn;
	
	/**
	 * 
	 * @param spawnQueueName	Name of Queue as a string
	 * @param component			Which component to remove		
	 * @param value				Time of component to remove
	 * @param frequencySpawn 	What list to remove from
	 */
	public Modification_AddSpawner(String spawnQueueName, Component component, double value, boolean frequencySpawn) {
		mySpawnQueue = spawnQueueName;
		mySpawnData = new SpawnData(component, value);
		isFrequencySpawn = frequencySpawn;
	}
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		State state = myModel.getState();
		Map<String, SpawnQueue> spawnQueues = state.getSpawnQueues();
		if(!spawnQueues.containsKey(mySpawnQueue)){
			spawnQueues.put(mySpawnQueue, new SpawnQueue());
			System.out.println("ERROR: SPAWN QUEUE NOT INITIALIZED PREVIOUSLY");
		}
		SpawnQueue spawnQueue = spawnQueues.get(mySpawnQueue);
		if(isFrequencySpawn){
			spawnQueue.getFrequencyQueue().add(mySpawnData);
		} else{
			spawnQueue.getSpawnQueue().add(mySpawnData);
		}
	}

}
