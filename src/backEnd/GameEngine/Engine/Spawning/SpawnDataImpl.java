package backEnd.GameEngine.Engine.Spawning;

/**
 * Container class for Components to spawn with the time/frequency to spawn them
 * at
 * 
 * @author Alex
 *
 */
public class SpawnDataImpl implements SpawnData {

	String mySpawnable;
	double myFrequency;
	double myDelay;
	int mySpawns;

	public SpawnDataImpl(String component, double frequency, double delay, int spawns) {
			mySpawnable = component;
			myFrequency = frequency;
			myDelay     = delay;
			mySpawns    = spawns;
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
	}

}
