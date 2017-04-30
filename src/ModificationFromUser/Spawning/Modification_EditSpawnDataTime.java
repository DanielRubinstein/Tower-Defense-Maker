package ModificationFromUser.Spawning;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.GameEngine.Engine.Spawning.SpawnData;
import backEnd.GameEngine.Engine.Spawning.SpawnDataReader;

public class Modification_EditSpawnDataTime implements ModificationFromUser{

	private SpawnDataReader mySpawnDataReader;
	private double myNewTime;
	
	
	public Modification_EditSpawnDataTime(SpawnDataReader spawnData, double newValue) {
		mySpawnDataReader = spawnData;
		myNewTime = newValue;
	}


	@Override
	public void invoke(Model myModel) throws Exception {
		SpawnData mySpawnData = myModel.getSpawnData(mySpawnDataReader);
		mySpawnData.setTime(myNewTime);
	}
}
