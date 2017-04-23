package backEnd.GameData.Rules;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.GameData.Rules.EndCondition.HealthLoseCondition;
import backEnd.GameData.Rules.EndCondition.KillCountCondition;
import backEnd.GameData.Rules.EndCondition.ScoreWinCondition;
import backEnd.GameData.Rules.EndCondition.TimeEndCondition;
import javafx.beans.property.ReadOnlyDoubleWrapper;

public class RulesMap {

	private Map<String, Rule> myRules = new HashMap<String, Rule>();

	public RulesMap() {
		// FIXME make sure rules store KeyName, default val, DisplayString, max and min values
		addPresetRules();
		
	}
	
	public RulesMap(Map<String, Rule> myMap){
		myRules = myMap;
	}

	
	public Map<String, Rule> getMapWithKeyNames(){
		return myRules;
	}
	
	private void addPresetRules(){
		//FIXME what should the keys be in myRuleBank
		myRules.put("Health", new HealthLoseCondition());
		myRules.put("Time", new TimeEndCondition());
		myRules.put("Score", new ScoreWinCondition());
		myRules.put("KillCount", new KillCountCondition());
	}
	
	
	
}
