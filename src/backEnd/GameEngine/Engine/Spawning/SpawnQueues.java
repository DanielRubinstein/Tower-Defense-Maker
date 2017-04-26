package backEnd.GameEngine.Engine.Spawning;

import java.util.ArrayList;
import java.util.List;

import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;

/**
 * SpawnQueue object that is held in tiles to determine what needs to be spawned next
 * @author Alex
 *
 */
public class SpawnQueues implements SerializableObservable{
	
	private List<SpawnDataImpl> myFrequencySpawnQueue;
	private List<SpawnDataImpl> mySingleSpawnQueue;
	private double myTimeLastQueueSpawned;
	private int myCurrentSpawn;
	private double myGameTime;
	private List<SerializableObserver> observers;
	/**
	 * Initializes blank lists to spawn from
	 */
	public SpawnQueues() {
		myFrequencySpawnQueue = new ArrayList<SpawnDataImpl>();
		mySingleSpawnQueue	 = new ArrayList<SpawnDataImpl>();
		observers = new ArrayList<SerializableObserver>();
	}
	
	/**
	 * 
	 * @return frequencyQueue
	 */
	public List<SpawnDataImpl> getFrequencySpawnQueue(){
		return myFrequencySpawnQueue;
	}
	
	/**
	 * SpawnQueue return
	 * @return
	 */
	public List<SpawnDataImpl> getSingleSpawnQueue() {
		return mySingleSpawnQueue;
	}
	
	/**
	 * @return the next component in the spawn Queue if enough time has passed
	 */
	public String getNextSingleSpawn(double gameTime) {
		//System.out.println(this.getClass().getSimpleName() + ": " + myCurrentSpawn + " | " + mySpawnQueue.size() + " | " + gameTime + " | " + myTimeLastQueueSpawned + " | " + mySpawnQueue.get(myCurrentSpawn).getTime());
		if(myCurrentSpawn >= mySingleSpawnQueue.size() || gameTime - myTimeLastQueueSpawned < mySingleSpawnQueue.get(myCurrentSpawn).getTime()){
			return null;
		}
		return mySingleSpawnQueue.get(myCurrentSpawn).getPresetName();
	}
	
	/**
	 * 
	 * @param timePassed
	 * @return
	 */
	public List<String> getNextFrequencySpawn(double gameTime, double gameStep){
		List<String> spawnList = new ArrayList<String>();
		for (int i = 0; i < myFrequencySpawnQueue.size(); i++) {
			SpawnDataImpl spawnData = myFrequencySpawnQueue.get(i);
			double frequency = spawnData.getTime();
			double modFreq = gameTime % frequency;
			//System.out.println(this.getClass().getSimpleName() + " " + (gameStep > modFreq) + " : " + modFreq + " : " + gameTime + " : " + frequency);
			if(gameStep > modFreq){
				spawnList.add(myFrequencySpawnQueue.get(i).getPresetName());
			}
		}		
		return spawnList;
	}

	public void update(double gameTime) {
		myGameTime = gameTime;
		if(myCurrentSpawn >= mySingleSpawnQueue.size() || myGameTime - myTimeLastQueueSpawned < mySingleSpawnQueue.get(myCurrentSpawn).getTime()){} else {
			myTimeLastQueueSpawned = myGameTime;
			myCurrentSpawn++;
		}
	}

	public void add(SpawnDataImpl mySpawnData, boolean isFrequencySpawn) {
		if(isFrequencySpawn){
			myFrequencySpawnQueue.add(mySpawnData);
		} else{
			mySingleSpawnQueue.add(mySpawnData);
		}
		notifyObservers(mySpawnData);
	}

	private void notifyObservers(Object obj) {
		for (SerializableObserver o : observers){
			o.update(this, obj);
		}
	}

	@Override
	public void addObserver(SerializableObserver o) {
		observers.add(o);
	}

	@Override
	public List<SerializableObserver> getObservers() {
		return observers;
	}

	@Override
	public void clearObservers() {
		observers = null;
	}

	@Override
	public void setObservers(List<SerializableObserver> observersave) {
		observers = observersave;
	}
}
