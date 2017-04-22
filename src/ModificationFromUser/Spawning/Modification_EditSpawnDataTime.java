package ModificationFromUser.Spawning;

import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;

public class Modification_EditSpawnDataTime implements ModificationFromUser{

	private String mySpawnQueue;
	private boolean isFrequencySpawn;
	private Component myComponent;
	private long myOldTime;
	private long myNewTime;
	
	public Modification_EditSpawnDataTime(String spawnQueueName, Component component, long oldTime, long newTime, boolean frequencySpawn) {
		mySpawnQueue 		= spawnQueueName;
		myComponent 		= component;
		myOldTime			= oldTime;
		myNewTime			= newTime;
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
				if(spawnQueue.getFrequencyQueue().get(i).getSpawnable() == myComponent && spawnQueue.getFrequencyQueue().get(i).getTime() == myOldTime){
					spawnQueue.getFrequencyQueue().get(i).setTime(myNewTime);
				}
			}
		} else{
			for(int i = 0; i < spawnQueue.getSpawnQueue().size(); i++){
				if(spawnQueue.getSpawnQueue().get(i).getSpawnable() == myComponent && spawnQueue.getSpawnQueue().get(i).getTime() == myOldTime){
					spawnQueue.getSpawnQueue().get(i).setTime(myNewTime);
				}
			}
		}
	}
}