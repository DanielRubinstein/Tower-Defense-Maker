package backEnd.GameEngine.Engine.Spawning;

import java.util.ArrayList;
import java.util.List;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;

/**
 * SpawnQueue object that is held in tiles to determine what needs to be spawned
 * next
 * 
 * @author Alex
 *
 */
public class SpawnQueues implements SerializableObservable {

	private List<SpawnDataImpl> mySpawnQueue;
	private int myCurrentSpawn;
	private List<SerializableObserver> observers = new ArrayList<SerializableObserver>();

	/**
	 * Initializes blank lists to spawn from
	 */
	public SpawnQueues() {
		mySpawnQueue = new ArrayList<SpawnDataImpl>();
	}

	public SpawnQueues(SpawnQueueInstantiator i) {

		mySpawnQueue = i.getSpawnQueue();
		myCurrentSpawn = i.getCurrentSpawn();
	}

	public SpawnQueueInstantiator getInstantiator() {
		return new SpawnQueueInstantiator(mySpawnQueue, myCurrentSpawn);
	}

	/**
	 * SpawnQueue return
	 * 
	 * @return
	 */
	public List<? extends SpawnDataReader> getSingleSpawnQueue() {
		return mySpawnQueue;
	}

	public List<String> getNextSpawns(double gameTime, double gameStep) {
		List<String> spawnList = new ArrayList<String>();
		for (int i = 0; i < mySpawnQueue.size(); i++) {
			SpawnData currentSpawnData = mySpawnQueue.get(i);
			//System.out.println(this.getClass().getSimpleName() + ": Delay: " + currentSpawnData.getDelay() + " | Frequency: " + currentSpawnData.getFrequency() + " | Spawns left: " + currentSpawnData.getSpawns());
			double frequency = currentSpawnData.getFrequency();
			double modFreq = (gameTime - currentSpawnData.getDelay()) % frequency;
			if(gameTime < currentSpawnData.getDelay() || currentSpawnData.getSpawns() <= 0 || gameStep <= modFreq){
				//System.out.println(this.getClass().getSimpleName() + ": Not spawning");
				continue;
			}
			spawnList.add(mySpawnQueue.get(i).getPresetName());
			currentSpawnData.setRecentSpawn(true);
		}
		return spawnList;
	}

	public void update() {
		for (int i = 0; i < mySpawnQueue.size(); i++) {
			SpawnData currentSpawnData = mySpawnQueue.get(i);
			if(currentSpawnData.isRecentSpawn()){
				currentSpawnData.setRecentSpawn(false);
				currentSpawnData.setSpawns(currentSpawnData.getSpawns() - 1);
				//System.out.println(this.getClass().getSimpleName() + ": Spawns: " + currentSpawnData.getSpawns());
				if(currentSpawnData.getSpawns() == 0){
					//TODO remove? Or no?
				}
			}
		}
	}

	public void add(SpawnDataImpl mySpawnData) {
		mySpawnQueue.add(mySpawnData);
		notifyObservers(mySpawnData);
	}

	public void remove(SpawnDataReader mySpawnDataToRemove) {
		mySpawnQueue.remove(mySpawnDataToRemove);
		this.notifyObservers(mySpawnDataToRemove);
	}
	
	private void notifyObservers(Object obj) {
		for (SerializableObserver o : observers) {
			o.update(this, obj);
		}
	}
	
	public void setInitialSpawnIterations(){
		for(SpawnDataImpl spawn : mySpawnQueue){
			spawn.setStartingIterations();
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

	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}
}
