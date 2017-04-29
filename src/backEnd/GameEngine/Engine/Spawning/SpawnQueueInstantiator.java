package backEnd.GameEngine.Engine.Spawning;

import java.util.List;

public class SpawnQueueInstantiator
{
	private List<SpawnDataImpl> myFrequencyQueue;
	private List<SpawnDataImpl> mySpawnQueue;
	private double myTimeLastQueueSpawned;
	private int myCurrentSpawn;
	private double myGameTime;
	
	public SpawnQueueInstantiator(List<SpawnDataImpl> myFrequencyQueue, List<SpawnDataImpl> mySpawnQueue, double myTimeLastQueueSpawned, int myCurrentSpawn,
			double myGameTime)
	{
		this.myFrequencyQueue = myFrequencyQueue;
		this.mySpawnQueue = mySpawnQueue;
		this.myTimeLastQueueSpawned = myTimeLastQueueSpawned;
		this.myCurrentSpawn = myCurrentSpawn;
		this.myGameTime = myGameTime;
	}
	
	public List<SpawnDataImpl> getFrequencyQueue()
	{
		return myFrequencyQueue;
	}
	public List<SpawnDataImpl> getSpawnQueue()
	{
		return mySpawnQueue;
	}
	
	public double getLastSpawnTime()
	{
		return myTimeLastQueueSpawned;
	}
	public int getCurrentSpawn()
	{
		return myCurrentSpawn;
	}
	public double getGameTime()
	{
		return myGameTime;
	}
}
