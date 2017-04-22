package backEnd.GameData;

import java.util.List;

import backEnd.GameData.Rules.Rule;
import backEnd.GameData.State.PlayerStatus;
import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameEngine.EngineStatus;


public class GameData implements GameDataInterface{
	private StateImpl myState;
	private List<Rule> myRules;
	private PlayerStatus myPlayerStatus;
	private EngineStatus myEngineStatus;
	
	public GameData(StateImpl state, PlayerStatus playerStatus, List<Rule> rules){
		this.myState = state;
		this.myRules = rules;
		this.myPlayerStatus = playerStatus;
		myEngineStatus=EngineStatus.PAUSED;
		myState.setEngineStatus(myEngineStatus);
	}

	@Override
	public State getState() {
		return myState;
	}

	@Override
	public List<Rule> getRules() {
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

}
