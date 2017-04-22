package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.Rules.Rule;

public abstract class EndCondition implements Rule{
	
	private double myVal;
	
	public EndCondition(double val){
		myVal = val;
	}
	
	@Override
	public void setVal(double newVal){
		myVal = newVal;
	}
	
	@Override
	public double getVal(){
		return myVal;
	}

}
