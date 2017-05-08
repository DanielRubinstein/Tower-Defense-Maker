package backEnd.GameEngine.Engine;

import backEnd.GameData.GameData;
import backEnd.GameData.Rules.Rule;
import backEnd.LevelProgression.LevelProgressionControllerImpl;

/**
 * @author Derek
 *
 */
public class RulesEngine implements Engine
{
	
	@Override
	public void gameLoop(GameData myGameData, double stepTime) {
		for(Rule rule :myGameData.getRules().getRuleCollection()){
			
			rule.invoke(myGameData);
		}
	}


}
