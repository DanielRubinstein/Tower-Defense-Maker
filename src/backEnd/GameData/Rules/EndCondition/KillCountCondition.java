package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;

public class KillCountCondition extends WinCondition {

	private static final double DEFAULT_ENEMIES = 100;

	public KillCountCondition() {
		super(DEFAULT_ENEMIES);
	}

	@Override
	public void invoke(GameData myGameData) {
		if (myGameData.getModifiablePlayerStatus().getStatusItemValue("KillCount") >= getVal()) {
			winGame();
		}

	}

}
