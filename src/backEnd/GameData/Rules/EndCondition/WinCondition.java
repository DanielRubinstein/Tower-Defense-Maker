package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;
import backEnd.GameData.Rules.Rule;
import backEnd.LevelProgression.LevelProgressionControllerImpl;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import data.XMLReadingException;
import data.GamePrep.DataInputLoader;

public abstract class WinCondition extends Rule {

	public WinCondition(double val, double minVal, double maxVal, String keyName, String displayString) {
		super(val, minVal, maxVal, keyName, displayString);
	}

	protected void winGame(GameData myGameData) {
		// ask level progress controller for next level
		LevelProgressionControllerReader myLPC = myGameData.getLevelProgressionController();

		String levelName = myLPC.getNextLevel();

		// load in new level
		if (levelName != null) {
			try {
				DataInputLoader loader = new DataInputLoader(levelName);
				myGameData = loader.getGameData();
			} catch (XMLReadingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// if there is no next level, go to end game win screen
		}
	}

}
