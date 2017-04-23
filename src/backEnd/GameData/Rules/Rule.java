package backEnd.GameData.Rules;

import backEnd.GameData.GameData;

public abstract class Rule implements RuleReader{

	private double myVal;
	private boolean enabled = false;

	public Rule(double val) {
		myVal = val;
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

	public abstract void invoke(GameData myGameData);

}
