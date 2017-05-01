package backEnd.GameEngine.Engine.Spawning;

public interface SpawnDataReader {
	
	public String getPresetName();
	
	public double getFrequency();

	public double getDelay();

	int getSpawns();
	
}
