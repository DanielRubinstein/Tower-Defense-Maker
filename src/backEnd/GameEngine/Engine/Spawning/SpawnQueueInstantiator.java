package backEnd.GameEngine.Engine.Spawning;

import java.util.List;

/**
 * 
 * @author Juan
 * @author Alex
 *
 */
public class SpawnQueueInstantiator {
	private List<SpawnDataImpl> mySpawnQueue;
	private int myCurrentSpawn;

	public SpawnQueueInstantiator(List<SpawnDataImpl> spawnQueue, int currentSpawn) {
		this.mySpawnQueue = spawnQueue;
		this.myCurrentSpawn = currentSpawn;
	}

	public List<SpawnDataImpl> getSpawnQueue() {
		return mySpawnQueue;
	}

	public int getCurrentSpawn() {
		return myCurrentSpawn;
	}
}
