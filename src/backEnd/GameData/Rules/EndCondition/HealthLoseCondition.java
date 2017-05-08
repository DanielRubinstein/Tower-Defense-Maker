package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;

/**
 * @author Derek
 *
 */
public class HealthLoseCondition extends LoseCondition {

	private static final double DEFAULT_HEALTH = 0;
	private static final double MAX_HEALTH = 100;
	private static final double MIN_HEALTH = 0;
	private static final String KEY_NAME = "Health";
	private static final String DISPLAY_STRING = "Health value to lose the level";
	
	public HealthLoseCondition() {
		super(DEFAULT_HEALTH, MIN_HEALTH, MAX_HEALTH, KEY_NAME, DISPLAY_STRING);
	}

	@Override
	public void invoke(GameData myGameData) {
		if (myGameData.getModifiablePlayerStatus().getStatusItemValue("Health") <= getVal() && this.isEnabled()) {
			loseGame(myGameData);
		}

	}

}
