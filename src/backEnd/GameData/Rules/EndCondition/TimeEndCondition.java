package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;

/**
 * @author Derek
 *
 */
public class TimeEndCondition extends WinCondition{
	
	private static final double DEFAULT_TIME = 100;
	private static final double MAX_TIME = 1000;
	private static final double MIN_TIME = 10;
	private static final String KEY_NAME = "Time";
	private static final String DISPLAY_STRING = "Time to win the level";
	
	public TimeEndCondition() {
		super(DEFAULT_TIME, MIN_TIME, MAX_TIME, KEY_NAME, DISPLAY_STRING);
	}

	@Override
	public void invoke(GameData myGameData) {
		if(myGameData.getGameTime() >= getVal()&& this.isEnabled()){
			winGame(myGameData);
		}
		
	}

}
