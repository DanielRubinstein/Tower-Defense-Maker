package backEnd;

import backEnd.State.State;

public class GameData implements GameDataInterface{
	private State myState;
	private Rules myRules;

	@Override
	public State getState() {
		return myState;
	}

	@Override
	public Rules getRules() {
		return myRules;
	}

}
