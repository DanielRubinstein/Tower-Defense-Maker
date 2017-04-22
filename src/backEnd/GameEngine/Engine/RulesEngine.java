package backEnd.GameEngine.Engine;

import backEnd.GameData.GameData;
import backEnd.GameData.Rules.Rule;

public class RulesEngine implements Engine{

	@Override
	public void gameLoop(GameData myGameData, double stepTime) {
		for(Rule rule :myGameData.getRules()){
			rule.invoke(myGameData);
		}
	}

}
