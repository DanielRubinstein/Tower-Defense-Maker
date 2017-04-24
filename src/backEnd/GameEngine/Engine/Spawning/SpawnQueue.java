package backEnd.GameEngine.Engine.Spawning;

import java.util.ArrayList;
import java.util.List;

import backEnd.GameData.State.Component;
import data.GamePrep.GameMaker;
/**
 * SpawnQueue object that is held in tiles to determine what needs to be spawned next
 * @author Alex
 *
 */
public class SpawnQueue {
	
	private List<SpawnData> myFrequencyQueue;
	private List<SpawnData> mySpawnQueue;
	private double myTimeLastQueueSpawned;
	private int myCurrentSpawn;
	private double myGameTime;
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
	public Component getNextQueueSpawn(double gameTime) {
		//System.out.println(this.getClass().getSimpleName() + ": " + myCurrentSpawn + " | " + mySpawnQueue.size() + " | " + gameTime + " | " + myTimeLastQueueSpawned + " | " + mySpawnQueue.get(myCurrentSpawn).getTime());
		if(myCurrentSpawn >= mySpawnQueue.size() || gameTime - myTimeLastQueueSpawned < mySpawnQueue.get(myCurrentSpawn).getTime()){
			return null;
		}
		return mySpawnQueue.get(myCurrentSpawn).getSpawnable();
	}
	
	/**
	 * 
	 * @param timePassed
	 * @return
	 */
	public List<Component> getNextFrequencySpawn(double gameTime, double gameStep){
		List<Component> spawnList = new ArrayList<Component>();
		for (int i = 0; i < myFrequencyQueue.size(); i++) {
			SpawnData spawnData = myFrequencyQueue.get(i);
			double frequency = spawnData.getTime();
			double modFreq = gameTime % frequency;
			//System.out.println(this.getClass().getSimpleName() + " " + (gameStep > modFreq) + " : " + modFreq + " : " + gameTime + " : " + frequency);
			if(gameStep > modFreq){
				spawnList.add(myFrequencyQueue.get(i).getSpawnable());
			}
		}		
		return spawnList;
	}
	
	public void update(double gameTime) {
		myGameTime = gameTime;
		if(myCurrentSpawn >= mySpawnQueue.size() || myGameTime - myTimeLastQueueSpawned < mySpawnQueue.get(myCurrentSpawn).getTime()){} else {
			myTimeLastQueueSpawned = myGameTime;
			myCurrentSpawn++;
		}
	}
}
