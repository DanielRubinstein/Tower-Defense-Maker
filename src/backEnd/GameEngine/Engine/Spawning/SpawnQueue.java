package backEnd.GameEngine.Engine.Spawning;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import backEnd.GameData.State.Component;
/**
 * SpawnQueue object that is held in tiles to determine what needs to be spawned next
 * @author Alex
 *
 */
public class SpawnQueue extends Observable{
	
	private List<SpawnData> myFrequencyQueue;
	private List<SpawnData> mySpawnQueue;
	private double myTimeLastQueueSpawned;
	private int myCurrentSpawn;
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
		if(myCurrentSpawn >= mySpawnQueue.size() || gameTime - myTimeLastQueueSpawned < mySpawnQueue.get(myCurrentSpawn).getTime()){
			return null;
		}
		myTimeLastQueueSpawned = gameTime;
		return mySpawnQueue.get(myCurrentSpawn++).getSpawnable();
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
			System.out.println((gameStep > modFreq) + " : " + modFreq + " : " + gameTime + " : " + frequency);
			if(gameStep > modFreq){
				spawnList.add(myFrequencyQueue.get(i).getSpawnable());
			}
		}		
		return spawnList;
	}
	
	public void addAsListener(Observer o) {
		addObserver(o);
	}

	public void add(SpawnData mySpawnData, boolean isFrequencySpawn) {
		if(isFrequencySpawn){
			myFrequencyQueue.add(mySpawnData);
			this.notifyObservers(myFrequencyQueue);
		} else{
			mySpawnQueue.add(mySpawnData);
			this.notifyObservers(mySpawnQueue);
		}
	}
	
}
