package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;
import backEnd.GameData.Rules.Rule;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import frontEnd.Skeleton.SplashScreens.SplashScreenType;

/**
 * @author Derek
 *
 */
public abstract class LoseCondition extends Rule{
	
	

	public LoseCondition(double val, double minVal, double maxVal, String keyName, String displayString) {
		super(val, minVal, maxVal, keyName, displayString);
	}

	protected void loseGame(GameData myGameData){
		LevelProgressionControllerEditor myLPC = myGameData.getLevelProgressionController();
		myLPC.initiateSplashScreen(SplashScreenType.LEVEL_LOST, myGameData.getReadOnlyPlayerStatus());
	}

}
