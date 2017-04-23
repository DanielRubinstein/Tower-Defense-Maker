package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.Rules.Rule;

public abstract class WinCondition extends Rule{
	
	public WinCondition(double val) {
		super(val);
	}

	
	protected void winGame(){
		//ask level progress controller for next level
		//load in new level
		//if there is no next level, go to end game win screen
	}

}
