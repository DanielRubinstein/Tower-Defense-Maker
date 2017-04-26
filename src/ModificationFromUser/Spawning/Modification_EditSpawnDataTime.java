package ModificationFromUser.Spawning;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameEngine.Engine.Spawning.SpawnData;
import backEnd.GameEngine.Engine.Spawning.SpawnDataReader;

public class Modification_EditSpawnDataTime implements ModificationFromUser{

	private SpawnData mySpawnData;
	private double myNewTime;
	
	
	public Modification_EditSpawnDataTime(SpawnDataReader spawnData, double newValue) {
		mySpawnData = (SpawnData) spawnData;
		myNewTime = newValue;
	}


	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		mySpawnData.setTime(myNewTime);
	}
}
