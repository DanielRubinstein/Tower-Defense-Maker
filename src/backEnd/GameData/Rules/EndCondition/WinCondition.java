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
		System.out.println("kill count reached");
		//load splash screen and action to occur after splash screen
		myLPC.initiateSplashScreen(new SplashScreenData("You won the level!\nPress any key to continue to the next level", SplashScreenType.LEVEL_WON, () -> myLPC.loadNextGame()));
		System.out.println("kill count reached 2");
	}

}
