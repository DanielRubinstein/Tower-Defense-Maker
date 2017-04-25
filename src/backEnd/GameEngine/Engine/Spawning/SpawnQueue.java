package backEnd.GameEngine.Engine.Spawning;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
	public String getNextQueueSpawn(double gameTime) {
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
	public List<String> getNextFrequencySpawn(double gameTime, double gameStep){
		List<String> spawnList = new ArrayList<String>();
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

	public void addAsListener(Observer o) {
		addObserver(o);
	}

	public void add(SpawnData mySpawnData, boolean isFrequencySpawn) {
		System.out.println("Adding");
		if(isFrequencySpawn){
			myFrequencyQueue.add(mySpawnData);
			this.notifyObservers("Hey");
		} else{
			mySpawnQueue.add(mySpawnData);
			this.notifyObservers(mySpawnQueue);
		}
	}
}
