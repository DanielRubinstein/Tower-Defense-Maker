package ModificationFromUser.Spawning;

import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;

public class Modification_EditSpawnDataComponent implements ModificationFromUser{

	private String mySpawnQueue;
	private boolean isFrequencySpawn;
	private Component myOldComponent;
	private Component myNewComponent;
	private long myTime;
	
	public Modification_EditSpawnDataComponent(String spawnQueueName, Component oldComponent, Component newComponent, long time, boolean frequencySpawn) {
		mySpawnQueue 		= spawnQueueName;
		myOldComponent 		= oldComponent;
		myNewComponent 		= newComponent;
		myTime 				= time;
		isFrequencySpawn 	= frequencySpawn;	
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
				if(spawnQueue.getFrequencyQueue().get(i).getSpawnable() == myOldComponent && spawnQueue.getFrequencyQueue().get(i).getTime() == myTime){
					spawnQueue.getFrequencyQueue().get(i).setSpawnable(myNewComponent);
				}
			}
		} else{
			for(int i = 0; i < spawnQueue.getSpawnQueue().size(); i++){
				if(spawnQueue.getSpawnQueue().get(i).getSpawnable() == myOldComponent && spawnQueue.getSpawnQueue().get(i).getTime() == myTime){
					spawnQueue.getSpawnQueue().get(i).setSpawnable(myNewComponent);
				}
			}
		}
	}

}
