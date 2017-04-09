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

}
