package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;

/**
 * @author Derek
 *
 */
public class ScoreWinCondition extends WinCondition {

	private static final double DEFAULT_SCORE = 10;
	private static final double MAX_SCORE = 1000;
	private static final double MIN_SCORE = 0;
	private static final String KEY_NAME = "Score";
	private static final String DISPLAY_STRING = "Score to win the level";
	
	public ScoreWinCondition() {
		super(DEFAULT_SCORE, MIN_SCORE, MAX_SCORE, KEY_NAME, DISPLAY_STRING);
	}
	
	@Override
	public void invoke(GameData myGameData) {
		if (myGameData.getModifiablePlayerStatus().getStatusItemValue("Score") >= getVal() && this.isEnabled()) {
			winGame(myGameData);
		}

	}

}
