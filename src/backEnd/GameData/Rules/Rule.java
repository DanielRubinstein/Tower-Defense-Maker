package backEnd.GameData.Rules;

import backEnd.GameData.GameData;
import backEnd.LevelProgression.LevelProgressionControllerImpl;

public abstract class Rule{

	private double myVal;
	private double minVal;
	private double maxVal;
	private boolean enabled = false;
	private String myKeyName;
	private String myDisplayString;

	public Rule(double val, double minVal, double maxVal, String keyName, String displayString) {
		this.myVal = val;
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.myKeyName = keyName;
		this.myDisplayString = displayString;
	}

	public void setVal(double newVal) {
		myVal = newVal;
	}

	
	public double getVal() {
		return myVal;
	}

	public void toggle() {
		enabled = !enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public double getMaxVal(){
		return maxVal;
	}
	
	public double getMinVal(){
		return minVal;
	}
	
	public String getKeyName(){
		return myKeyName;
	}
	
	public String getDisplayString(){
		return myDisplayString;
	}

	public abstract void invoke(GameData myGameData);

}
