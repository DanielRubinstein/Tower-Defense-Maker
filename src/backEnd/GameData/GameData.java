package backEnd.GameData;

import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;

public class GameData implements GameDataInterface{
	private StateImpl myState;
	private Rules myRules;
	
	public GameData(StateImpl state, Rules rules){
		this.myState = state;
		this.myRules = rules;
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

}
