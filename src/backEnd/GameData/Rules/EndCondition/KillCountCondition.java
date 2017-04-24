package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;

public class KillCountCondition extends WinCondition {

	private static final double DEFAULT_KILL_COUNT = 0;
	private static final double MAX_KILL_COUNT = 100;
	private static final double MIN_KILL_COUNT = 0;
	private static final String KEY_NAME = "KillCount";
	private static final String DISPLAY_STRING = "Kill count to win the level";
	
	public KillCountCondition() {
		super(DEFAULT_KILL_COUNT, MIN_KILL_COUNT, MAX_KILL_COUNT, KEY_NAME, DISPLAY_STRING);
	}

	@Override
	public void invoke(GameData myGameData) {
		if (myGameData.getModifiablePlayerStatus().getStatusItemValue("KillCount") >= getVal() && this.isEnabled()) {
			winGame(myGameData);
		}

	}

}
