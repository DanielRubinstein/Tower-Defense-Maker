package ModificationFromUser.Spawning;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import backEnd.GameEngine.Engine.Spawning.SpawnData;

public class Modification_RemoveSpawner implements ModificationFromUser{

	private String mySpawnQueue;

	public Modification_RemoveSpawner(String spawnQueueName, Component component, long time, boolean frequencySpawn) {
		mySpawnQueue = spawnQueueName;
		isFrequencySpawn = frequencySpawn;
	}
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
