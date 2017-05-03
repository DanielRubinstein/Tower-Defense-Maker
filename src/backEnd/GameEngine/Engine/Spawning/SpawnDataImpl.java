package backEnd.GameEngine.Engine.Spawning;

/**
 * Container class for Components to spawn with the time/frequency to spawn them
 * at
 * 
 * @author Alex
 *
 */
public class SpawnDataImpl implements SpawnData {

	private String mySpawnable;
	private double myFrequency;
	private double myDelay;
	private int mySpawns;
	private int startingSpawns;
	private boolean recentSpawn;

	public SpawnDataImpl(String component, double frequency, double delay, int spawns) {
		mySpawnable = component;
		myFrequency = frequency;
		myDelay = delay;
		mySpawns = spawns;
		startingSpawns = spawns;
	}

	@Override
	public String getPresetName() {
		return mySpawnable;
	}

	@Override
	public double getFrequency() {
		return myFrequency;
	}

	@Override
	public void setFrequency(double myFrequency) {
		this.myFrequency = myFrequency;
	}

	@Override
	public double getDelay() {
		return myDelay;
	}

	@Override
	public void setDelay(double myDelay) {
		this.myDelay = myDelay;
	}

	@Override
	public int getSpawns() {
		return mySpawns;
	}

	@Override
	public void setSpawns(int mySpawns) {
		this.mySpawns = mySpawns;
		this.startingSpawns = mySpawns;
	}

	@Override
	public boolean isRecentSpawn() {
		return recentSpawn;
	}

	@Override
	public void setRecentSpawn(boolean recentSpawn) {
		this.recentSpawn = recentSpawn;
	}

	@Override
	public void setStartingIterations() {
		mySpawns = startingSpawns;
		
	}

}
