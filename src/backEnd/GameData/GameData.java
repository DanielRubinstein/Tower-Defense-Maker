package backEnd.GameData;

import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameEngine.EngineStatus;

public class GameData implements GameDataInterface{
	private StateImpl myState;
	private Rules myRules;
	private EngineStatus myEngineStatus;
	
	public GameData(StateImpl state, Rules rules){
		this.myState = state;
		this.myRules = rules;
		myEngineStatus=EngineStatus.PAUSED;
		myState.setEngineStatus(myEngineStatus);
	}

	@Override
	public State getState() {
		return myState;
	}

	@Override
	public Rules getRules() {
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

}
