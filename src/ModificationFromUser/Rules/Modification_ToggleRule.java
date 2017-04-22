package ModificationFromUser.Rules;

import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.Rules.Rule;

public class Modification_ToggleRule implements ModificationFromUser{

	private String myRuleName;

	public Modification_ToggleRule(String ruleName) {
		myRuleName = ruleName;
	}
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		Map<String, Rule> ruleMap = myModel.getGameData().getRules();
		//ruleMap.get(myRuleName).toggle();
	}

}
