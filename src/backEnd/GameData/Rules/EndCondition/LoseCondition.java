package backEnd.GameData.Rules.EndCondition;

public abstract class LoseCondition extends EndCondition{
	
	public LoseCondition(double val) {
		super(val);
	}

	protected void loseGame(){
		//stop game animation timeline/keyframe
		//present end game screen of loss
	}

}
