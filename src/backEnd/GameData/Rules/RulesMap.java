package backEnd.GameData.Rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.GameData.Rules.EndCondition.HealthLoseCondition;
import backEnd.GameData.Rules.EndCondition.KillCountCondition;
import backEnd.GameData.Rules.EndCondition.ScoreWinCondition;
import backEnd.GameData.Rules.EndCondition.TimeEndCondition;
import javafx.beans.property.ReadOnlyDoubleWrapper;

/**
 * 
 * @author Derek
 * @author Juan
 *
 */
public class RulesMap {

	private Map<String, Rule> myRules;

	public RulesMap()
	{
		myRules = new HashMap<String, Rule>();
		addPresetRules();
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
	
	public List<RuleReader> getRuleReaderList(){
		List<RuleReader> myReaderList = new ArrayList<RuleReader>();
		for(Rule rule : myRules.values()){
			myReaderList.add(rule);
		}
		return myReaderList;
		
	}
	
	public Collection<Rule> getRuleCollection(){
		return myRules.values();
	}
	
	public Rule get(String ruleName){
		return myRules.get(ruleName);
	}
	
	
	
}
