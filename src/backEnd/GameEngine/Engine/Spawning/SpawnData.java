package backEnd.GameEngine.Engine.Spawning;

import backEnd.GameData.State.Component;
/**
 * Container class for Components to spawn with the time/frequency to spawn them at
 * @author Alex
 *
 */
public class SpawnData implements Comparable<SpawnData>{

	Component mySpawnable;
	long myTime;
	
	public SpawnData(Component component, long time) {
		//NOT SURE IF COMPONENT IS ACTUALLY THE WAY TO GO HERE TODO CHANGE TO SOMETHING BETTER?
		mySpawnable = component;
		myTime = time;
	}
	
	public Component getSpawnable() {
		return mySpawnable;
	}
	
	public long getTime() {
		return myTime;
	}

	public void setSpawnable(Component mySpawnable) {
		this.mySpawnable = mySpawnable;
	}
	
	public void setTime(long myTime) {
		this.myTime = myTime;
	}
	
	@Override
	public int compareTo(SpawnData o) {
		return (int)((this.getTime()-o.getTime()) * 1000);
	}
}
