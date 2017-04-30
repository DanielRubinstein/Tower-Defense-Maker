package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;
import backEnd.GameData.Rules.Rule;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import frontEnd.Skeleton.SplashScreens.SplashScreenData;
import frontEnd.Skeleton.SplashScreens.SplashScreenType;

public abstract class WinCondition extends Rule {

	public WinCondition(double val, double minVal, double maxVal, String keyName, String displayString) {
		super(val, minVal, maxVal, keyName, displayString);
	}

	protected void winGame(GameData myGameData) {
		LevelProgressionControllerEditor myLPC = myGameData.getLevelProgressionController();

		myLPC.initiateSplashScreen(new SplashScreenData("You won the level!\nPress any key to continue to the next level", SplashScreenType.LEVEL_WON, () -> myLPC.loadNextLevel()));
	}

}
