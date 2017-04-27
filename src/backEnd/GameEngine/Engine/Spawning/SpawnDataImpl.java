package backEnd.GameEngine.Engine.Spawning;

/**
 * Container class for Components to spawn with the time/frequency to spawn them at
 * @author Alex
 *
 */
public class SpawnDataImpl implements Comparable<SpawnDataImpl>, SpawnData{

	String mySpawnable;
	double myTime;
	
	public SpawnDataImpl(String component, double time) {
		mySpawnable = component;
		myTime = time;
	}
	
	public String getPresetName() {
		return mySpawnable;
	}
	
	public double getTime() {
		return myTime;
	}
	
	public void setTime(Double myNewTime) {
		this.myTime = myNewTime;
	}
	
	@Override
	public int compareTo(SpawnDataImpl o) {
		return (int)(this.getTime()-o.getTime());
	}

}
