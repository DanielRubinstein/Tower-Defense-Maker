package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;

public class HealthLoseCondition extends LoseCondition {
	
	private static final double DEFAULT_HEALTH = 100;
	
	public HealthLoseCondition() {
		super(DEFAULT_HEALTH);
	}

	@Override
	public void invoke(GameData myGameData) {
		if (myGameData.getModifiablePlayerStatus().getStatusItemValue("Health") <= getVal() && this.isEnabled()) {
			loseGame();
		}

	}

}
