package backEnd.GameEngine.Engine.Spawning;

public interface SpawnData extends SpawnDataReader{

	void setFrequency(double myFrequency);

	void setDelay(double myDelay);

	void setSpawns(int mySpawns);

	void setRecentSpawn(boolean recentSpawn);
	
	void setStartingIterations();
	
	void setStartingSpawns(int spawns);

}
