package ModificationFromUser.Rules;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;

public class Modification_ToggleRule implements ModificationFromUser {
	
	String ruleName;
	
	public Modification_ToggleRule(String ruleName){
		this.ruleName = ruleName;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myModel.getGameData().getRules().get(ruleName).toggle();

	}

}
