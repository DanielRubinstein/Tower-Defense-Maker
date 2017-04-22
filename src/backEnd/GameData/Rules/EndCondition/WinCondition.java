package backEnd.GameData.Rules.EndCondition;

public abstract class WinCondition extends EndCondition{
	
	public WinCondition(double val) {
		super(val);
	}

	
	protected void winGame(){
		//ask level progress controller for next level
		//load in new level
		//if there is no next level, go to end game win screen
	}

}
