package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;
import backEnd.GameData.Rules.Rule;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import frontEnd.Skeleton.SplashScreens.SplashScreenData;
import frontEnd.Skeleton.SplashScreens.SplashScreenType;

public abstract class LoseCondition extends Rule{
	
	

	public LoseCondition(double val, double minVal, double maxVal, String keyName, String displayString) {
		super(val, minVal, maxVal, keyName, displayString);
	}

	protected void loseGame(GameData myGameData){
		LevelProgressionControllerEditor myLPC = myGameData.getLevelProgressionController();
		myLPC.initiateSplashScreen(new SplashScreenData("You lost the level!\nPress any key to continue to restart the game", 
				SplashScreenType.LEVEL_LOST, () -> System.out.println("loading first level")));
		//() -> myLPC.loadFirstLevel()
	}

}
