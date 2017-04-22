package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.Rules.Rule;

public abstract class LoseCondition extends Rule{
	
	public LoseCondition(double val) {
		super(val);
	}

	protected void loseGame(){
		//stop game animation timeline/keyframe
		//present end game screen of loss
	}

}
