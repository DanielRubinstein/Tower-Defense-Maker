package ModificationFromUser.Spawning;

import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;

/**
 * Modification to remove spawner from a list
 * @author Alex
 *
 */
public class Modification_RemoveSpawner implements ModificationFromUser{

	private String mySpawnQueue;
	private boolean isFrequencySpawn;
	private Component myComponent;
	private long myTime;
	
	/**
	 * 
	 * @param spawnQueueName	Name of Queue as a string
	 * @param component			Which component to remove		
	 * @param time				Time of component to remove
	 * @param frequencySpawn 	What list to remove from
	 */
	public Modification_RemoveSpawner(String spawnQueueName, Component component, long time, boolean frequencySpawn) {
		mySpawnQueue = spawnQueueName;
		myComponent = component;
		myTime = time;
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
			for(int i = 0; i < spawnQueue.getFrequencyQueue().size(); i++){
				if(spawnQueue.getFrequencyQueue().get(i).getSpawnable() == myComponent && spawnQueue.getFrequencyQueue().get(i).getTime() == myTime){
					spawnQueue.getFrequencyQueue().remove(i);
				}
			}
		} else{
			for(int i = 0; i < spawnQueue.getSpawnQueue().size(); i++){
				if(spawnQueue.getSpawnQueue().get(i).getSpawnable() == myComponent && spawnQueue.getSpawnQueue().get(i).getTime() == myTime){
					spawnQueue.getSpawnQueue().remove(i);
				}
			}
		}
	}
}
