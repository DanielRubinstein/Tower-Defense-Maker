package ModificationFromUser.Rules;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;

public class Modification_EditRuleValue implements ModificationFromUser{

	private String ruleName;
	private double newVal; 
	
	public Modification_EditRuleValue(String ruleName, double newVal) {
		this.ruleName = ruleName;
		this.newVal = newVal;
		
	}
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myModel.getGameData().getRules().get(ruleName).setVal(newVal);
		
	}

}
