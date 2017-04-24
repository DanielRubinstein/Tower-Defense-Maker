package backEnd.GameEngine.Engine.Spawning;

/**
 * Container class for Components to spawn with the time/frequency to spawn them at
 * @author Alex
 *
 */
public class SpawnData implements Comparable<SpawnData>{

	String mySpawnable;
	double myTime;
	
	public SpawnData(String component, double time) {
		mySpawnable = component;
		myTime = time;
	}
	
	public String getSpawnable() {
		return mySpawnable;
	}
	
	public double getTime() {
		return myTime;
	}

	public void setSpawnable(String presetName) {
		this.mySpawnable = presetName;
	}
	
	public void setTime(long myTime) {
		this.myTime = myTime;
	}
	
	@Override
	public int compareTo(SpawnData o) {
		return (int)(this.getTime()-o.getTime());
	}
}
