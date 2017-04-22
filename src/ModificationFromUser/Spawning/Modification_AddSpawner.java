package ModificationFromUser.Spawning;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import backEnd.GameEngine.Engine.Spawning.SpawnData;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;

public class Modification_AddSpawner implements ModificationFromUser{

	SpawnQueue mySpawnQueue;
	
	public Modification_AddSpawner(SpawnQueue spawnQueue, Component component, long time) {
		spawnQueue.getFrequencyQueue().add(new SpawnData(component, time));
	}
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
