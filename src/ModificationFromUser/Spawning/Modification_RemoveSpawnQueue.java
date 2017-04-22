package ModificationFromUser.Spawning;

import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;

public class Modification_RemoveSpawnQueue implements ModificationFromUser{

	private String mySpawnQueueName;
	
	public Modification_RemoveSpawnQueue(String spawnQueueName) {
		mySpawnQueueName = spawnQueueName;
	}
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		State state = myModel.getState();
		Map<String, SpawnQueue> spawnQueues = state.getSpawnQueues();
		if (spawnQueues.containsKey(mySpawnQueueName)) {
			spawnQueues.remove(mySpawnQueueName);
		} else {
			System.out.println("ERROR: SPAWN QUEUE ID NOT THERE TO REMOVE");
		}
	}

}
