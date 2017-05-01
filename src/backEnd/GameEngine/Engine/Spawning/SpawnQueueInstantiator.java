package backEnd.GameEngine.Engine.Spawning;

import java.util.List;

/**
 * 
 * @author Juan
 *
 */
public class SpawnQueueInstantiator {
	private List<SpawnDataImpl> mySpawnQueue;
	private double myTimeLastQueueSpawned;
	private int myCurrentSpawn;

	public SpawnQueueInstantiator(List<SpawnDataImpl> spawnQueue, double myTimeLastQueueSpawned, int myCurrentSpawn) {
		this.mySpawnQueue = spawnQueue;
		this.myTimeLastQueueSpawned = myTimeLastQueueSpawned;
		this.myCurrentSpawn = myCurrentSpawn;
	}

	public List<SpawnDataImpl> getSpawnQueue() {
		return mySpawnQueue;
	}

	public double getLastSpawnTime() {
		return myTimeLastQueueSpawned;
	}

	public int getCurrentSpawn() {
		return myCurrentSpawn;
	}
}
