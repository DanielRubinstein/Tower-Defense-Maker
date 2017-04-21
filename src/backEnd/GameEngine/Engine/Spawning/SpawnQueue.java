package backEnd.GameEngine.Engine.Spawning;

import java.util.ArrayList;
import java.util.List;

import backEnd.GameData.State.Component;
/**
 * SpawnQueue object that is held in tiles to determine what needs to be spawned next
 * @author Alex
 *
 */
public class SpawnQueue {
	
	List<SpawnData> myFrequencyQueue;
	List<SpawnData> mySpawnQueue;
	long timeLastSpawnedMillis;
	int myCurrentSpawn;
	
	public SpawnQueue() {
		myFrequencyQueue = new ArrayList<SpawnData>();
		mySpawnQueue	 = new ArrayList<SpawnData>();
	}
	
	public List<SpawnData> getFrequencyQueue(){
		return myFrequencyQueue;
	}
	
	public List<SpawnData> getSpawnQueue() {
		return mySpawnQueue;
	}
	
	/**
	 * Takes in the last time 
	 * @param time
	 * @return
	 */
	public Component getNextSpawn() {
		if(myCurrentSpawn >= mySpawnQueue.size() || (System.currentTimeMillis() - timeLastSpawnedMillis) / 1000 > mySpawnQueue.get(myCurrentSpawn).getTime()){
			return null;
		}
		return mySpawnQueue.get(myCurrentSpawn++).getSpawnable();
	}
	
	public List<Component> getNextFrequency(long timePassed){
		List<Component> spawnList = new ArrayList<Component>();
		for (int i = 0; i < myFrequencyQueue.size(); i++) {
			if(frequencySync(timePassed,myFrequencyQueue.get(i).getTime())){
				spawnList.add(myFrequencyQueue.get(i).getSpawnable());
			}
		}
		return spawnList;
	}
	
	/**
	 * 
	 * @param timePassed in Millis
	 * @param spawnFrequency
	 * @return
	 */
	private boolean frequencySync(long timePassed, long spawnFrequency) {
		return (timePassed / 1000) % spawnFrequency < 1.0/60;
	}
	
}
