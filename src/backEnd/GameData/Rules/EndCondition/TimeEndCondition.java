package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;

public class TimeEndCondition extends EndCondition{
	
	private static final double DEFAULT_TIME_LIMIT = 1000;

	public TimeEndCondition() {
		super(DEFAULT_TIME_LIMIT);
	}

	@Override
	public void invoke(GameData myGameData) {
		//if(myGameData.getTime() >= getVal()){winGame();}
		
	}

}
