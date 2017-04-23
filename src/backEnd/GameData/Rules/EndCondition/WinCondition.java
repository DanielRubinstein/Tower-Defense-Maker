package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;
import backEnd.GameData.Rules.Rule;
import backEnd.LevelProgression.LevelProgressionControllerImpl;
import data.XMLReadingException;
import data.GamePrep.DataInputLoader;

public abstract class WinCondition extends Rule{
	
	public WinCondition(double val) {
		super(val);
	}

	
	protected void winGame(GameData myGameData){
		//ask level progress controller for next level
		
		
		String levelName = /*myLPC.getNextLevel(gamename , currentLevel); */ "";
		
		
		//load in new level
		if (levelName != null)
		{
			try {
				DataInputLoader loader = new DataInputLoader(levelName);
				myGameData = loader.getGameData();
			} catch (XMLReadingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
		else
		{
			//if there is no next level, go to end game win screen
		}
	}

}
