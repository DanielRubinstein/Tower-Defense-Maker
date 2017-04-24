package backEnd.GameData;

import java.util.HashMap;
import java.util.Map;

import backEnd.GameData.Rules.Rule;
import backEnd.GameData.Rules.RulesMap;
import backEnd.GameData.Rules.EndCondition.HealthLoseCondition;
import backEnd.GameData.Rules.EndCondition.KillCountCondition;
import backEnd.GameData.Rules.EndCondition.ScoreWinCondition;
import backEnd.GameData.Rules.EndCondition.TimeEndCondition;
import backEnd.GameData.State.PlayerStatus;
import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameEngine.EngineStatus;
import backEnd.LevelProgression.LevelProgressionControllerReader;

public class GameData implements GameDataInterface{
	private StateImpl myState;
	private RulesMap myRules;
	private PlayerStatus myPlayerStatus;
	private EngineStatus myEngineStatus;
	private LevelProgressionControllerReader myLPC;
	private double myGameTime;
	
	public GameData(StateImpl state, PlayerStatus playerStatus, RulesMap myRules){
		this.myState = state;
		this.myRules = myRules;
		this.myPlayerStatus = playerStatus;
		myEngineStatus=EngineStatus.PAUSED;
		myState.setEngineStatus(myEngineStatus);
		myGameTime = (long) 0;
	}

	@Override
	public State getState() {
		return myState;
	}

	@Override
	public RulesMap getRules() {
		return myRules;
	}
	
	public void setEngineStatus(EngineStatus currentEngineStatus){
		myEngineStatus=currentEngineStatus;
	}
	
	/**
	 * copies info from newGameData and puts it in current GameData
	 * 
	 * @param newGameData
	 */
	public void updateGameData(GameData newGameData){
		//update state
		myState.updateState(newGameData.getState());
		//update rules
		myRules = newGameData.getRules();
		//update playerstatus
		updatePlayerStatus(newGameData.getStatus());
	}

	private void updatePlayerStatus(PlayerStatus newStatus){
		for(String key : myPlayerStatus.getKeySet()){
			myPlayerStatus.setStatusItemValue(key, newStatus.getStatusItemValue(key));
		}
	}
	
	@Override
	public PlayerStatusModifier getModifiablePlayerStatus() {
		return myPlayerStatus;
	}

	@Override
	public PlayerStatusReader getReadOnlyPlayerStatus() {
		return myPlayerStatus;
	}

	public PlayerStatus getStatus() {
		return this.myPlayerStatus;
	}

	public void incrementGameTime(Double gameStep) {
		myGameTime += gameStep;
	}
	
	public double getGameTime() {
		return myGameTime;
	}
	
	public void printRules(){
		for(Rule x: myRules.getMapWithKeyNames().values()){
			x.printRule();
		}
	}
	
	public void setLevelProgressionController(LevelProgressionControllerReader myLPC){
		this.myLPC = myLPC;
		
	}
	
	public LevelProgressionControllerReader getLevelProgressionController(){
		return myLPC;
	}
	

}