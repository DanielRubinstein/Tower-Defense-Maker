package ModificationFromUser.Spawning;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameEngine.Engine.Spawning.SpawnData;
import backEnd.GameEngine.Engine.Spawning.SpawnDataReader;

public class Modification_EditSpawnData implements ModificationFromUser{

	private SpawnDataReader mySpawnDataReader;
	private Double myNewFrequency;
	private Double myNewDelay;
	private Integer myNewSpawns;
	
	
	public Modification_EditSpawnData(SpawnDataReader spawnData, Double frequency, Double delay, Integer spawns) {
		mySpawnDataReader = spawnData;
		myNewFrequency = frequency;
		myNewDelay = delay;
		myNewSpawns = spawns;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		SpawnData mySpawnData = myModel.getSpawnData(mySpawnDataReader);
		mySpawnData.setDelay(myNewDelay);
		mySpawnData.setFrequency(myNewFrequency);
		mySpawnData.setSpawns(myNewSpawns);
	}
}
