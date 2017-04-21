package backEnd.GameData;

import backEnd.GameData.PlayerStatus.PlayerStatus;
import backEnd.GameData.PlayerStatus.PlayerStatusModifier;
import backEnd.GameData.PlayerStatus.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;

public class GameData implements GameDataInterface{
	private StateImpl myState;
	private Rules myRules;
	private PlayerStatus myPlayerStatus;
	
	public GameData(StateImpl state, PlayerStatus playerStatus, Rules rules){
		this.myState = state;
		this.myRules = rules;
		this.myPlayerStatus = playerStatus;
	}

	@Override
	public State getState() {
		return myState;
	}

	@Override
	public Rules getRules() {
		return myRules;
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

}
