package backEnd.GameData.Rules;

import backEnd.GameData.GameData;

public interface Rule {
	
	void invoke(GameData myGameData);
	
	void setVal(double newVal);
	
	double getVal();

}
