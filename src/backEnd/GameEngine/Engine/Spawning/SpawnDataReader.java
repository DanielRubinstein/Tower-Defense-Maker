package backEnd.GameEngine.Engine.Spawning;

public interface SpawnDataReader {
	
	String getPresetName();
	
	double getFrequency();

	double getDelay();

	int getSpawns();

	boolean isRecentSpawn();
	
}
