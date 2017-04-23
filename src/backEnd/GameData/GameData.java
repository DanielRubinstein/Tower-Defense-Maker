package backEnd.GameData;

import java.util.Map;

import backEnd.GameData.Rules.Rule;
import backEnd.GameData.State.PlayerStatus;
import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameEngine.EngineStatus;

public class GameData implements GameDataInterface{
	private StateImpl myState;
	private Map<String,Rule> myRules;
	private PlayerStatus myPlayerStatus;
	private EngineStatus myEngineStatus;
	private double myGameTime;
	
	public GameData(StateImpl state, PlayerStatus playerStatus, Map<String, Rule> rules){
		this.myState = state;
		this.myRules = rules;
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
	public Map<String, Rule> getRules() {
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
}