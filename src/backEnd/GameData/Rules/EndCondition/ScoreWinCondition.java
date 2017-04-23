package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;

public class ScoreWinCondition extends WinCondition {

	private static final double DEFAULT_SCORE = 100;

	public ScoreWinCondition() {
		super(DEFAULT_SCORE);
	}

	@Override
	public void invoke(GameData myGameData) {
		if (myGameData.getModifiablePlayerStatus().getStatusItemValue("Score") >= getVal() && this.isEnabled()) {
			winGame();
		}

	}

}
