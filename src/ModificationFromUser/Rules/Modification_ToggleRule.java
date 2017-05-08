package ModificationFromUser.Rules;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;

/**
 * @author Derek
 *
 */
public class Modification_ToggleRule implements ModificationFromUser {
	
	String ruleName;
	
	public Modification_ToggleRule(String ruleName){
		this.ruleName = ruleName;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		myModel.getGameData().getRules().get(ruleName).toggle();

	}

}
