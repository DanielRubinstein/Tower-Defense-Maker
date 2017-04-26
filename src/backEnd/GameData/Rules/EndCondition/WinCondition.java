package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;
import backEnd.GameData.Rules.Rule;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.LevelProgression.LevelProgressionControllerImpl;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import data.XMLReadingException;
import data.GamePrep.DataInputLoader;
import frontEnd.Skeleton.SplashScreens.SplashScreenData;
import frontEnd.Skeleton.SplashScreens.SplashScreenType;

public abstract class WinCondition extends Rule {

	public WinCondition(double val, double minVal, double maxVal, String keyName, String displayString) {
		super(val, minVal, maxVal, keyName, displayString);
	}

	protected void winGame(GameData myGameData) {
		// ask level progress controller for next level
		LevelProgressionControllerEditor myLPC = myGameData.getLevelProgressionController();

		//String levelName = myLPC.getNextLevel();
		
		//load splash screen
		myLPC.initiateSplashScreen(new SplashScreenData("hello", SplashScreenType.LEVEL_WON));
		

		// load in new level
		/*if (levelName != null) {
			try {
				DataInputLoader loader = new DataInputLoader(levelName);
				myGameData.updateGameData(loader.getGameData());
			} catch (XMLReadingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// if there is no next level, go to end game win screen
		} */
	}

}
