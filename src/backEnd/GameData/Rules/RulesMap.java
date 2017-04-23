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
		addPresetRules();
		
	}
	
	public RulesMap(Map<String, Rule> myMap){
		myRules = myMap;
	}

	
	public Map<String, Rule> getMapWithKeyNames(){
		return myRules;
	}
	
	private void addPresetRules(){
		Rule healthRule = new HealthLoseCondition();
		Rule timeRule = new TimeEndCondition();
		Rule scoreRule = new ScoreWinCondition();
		Rule killCountRule = new KillCountCondition();
		
		myRules.put(healthRule.getKeyName(), healthRule);
		myRules.put(timeRule.getKeyName(), timeRule);
		myRules.put(scoreRule.getKeyName(), scoreRule);
		myRules.put(killCountRule.getKeyName(), killCountRule);
	}
	
	
	
}
