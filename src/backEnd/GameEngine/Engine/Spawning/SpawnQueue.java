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
	long myTimeLastQueueSpawnedMillis;
	long myTimeLastFrequencyCheck;
	int myCurrentSpawn;
	/**
	 * Initializes blank lists to spawn from
	 */
	public SpawnQueue() {
		myFrequencyQueue = new ArrayList<SpawnData>();
		mySpawnQueue	 = new ArrayList<SpawnData>();
	}
	
	/**
	 * 
	 * @return frequencyQueue
	 */
	public List<SpawnData> getFrequencyQueue(){
		return myFrequencyQueue;
	}
	
	/**
	 * SpawnQueue return
	 * @return
	 */
	public List<SpawnData> getSpawnQueue() {
		return mySpawnQueue;
	}
	
	/**
	 * @return the next component in the spawn Queue if enough time has passed
	 */
	public Component getNextSpawn() {
		if(myCurrentSpawn >= mySpawnQueue.size() || (System.currentTimeMillis() - myTimeLastQueueSpawnedMillis) / 1000 > mySpawnQueue.get(myCurrentSpawn).getTime()){
			return null;
		}
		return mySpawnQueue.get(myCurrentSpawn++).getSpawnable();
	}
	
	/**
	 * 
	 * @param timePassed
	 * @return
	 */
	public List<Component> getNextFrequency(long timeStep){
		List<Component> spawnList = new ArrayList<Component>();
		for (int i = 0; i < myFrequencyQueue.size(); i++) {
			SpawnData spawnData = myFrequencyQueue.get(i);
			long frequency = spawnData.getTime();
			double minRange = ((myTimeLastFrequencyCheck)/1000) % frequency;
			double maxRange = ((myTimeLastFrequencyCheck + timeStep)/1000) % frequency;
			
			if(minRange > maxRange){
				spawnList.add(myFrequencyQueue.get(i).getSpawnable());
			}
			
		}
		
		myTimeLastFrequencyCheck += timeStep;
		
		return spawnList;
	}
	
}
