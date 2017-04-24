package backEnd.GameData.Rules;

import backEnd.GameData.GameData;
import backEnd.LevelProgression.LevelProgressionControllerImpl;

public abstract class Rule implements RuleReader{

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

	@Override
	public double getVal() {
		return myVal;
	}

	public void toggle() {
		enabled = !enabled;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public double getMaxVal(){
		return maxVal;
	}
	
	@Override
	public double getMinVal(){
		return minVal;
	}
	
	@Override
	public String getKeyName(){
		return myKeyName;
	}
	
	@Override
	public String getDisplayString(){
		return myDisplayString;
	}

	@Override
	public void printRule(){
		System.out.println(myKeyName + ": " + myVal + ": " + enabled);
	}
	
	public abstract void invoke(GameData myGameData);

}
