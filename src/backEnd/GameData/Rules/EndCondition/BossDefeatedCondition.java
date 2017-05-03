package backEnd.GameData.Rules.EndCondition;

import backEnd.GameData.GameData;

/**
 * @author Christian Martindale
 * Rule for detailing one possible win condition.
 * Upon killing a level boss, increments the level.
 *
 */
public class BossDefeatedCondition extends WinCondition{

		private static final double DEFAULT_BOSS_KILL_COUNT = 0;
		private static final double MAX_BOSS_KILL_COUNT = 100;
		private static final double MIN_BOSS_KILL_COUNT = 0;
		private static final String KEY_NAME = "BossKillCount";
		private static final String DISPLAY_STRING = "Bosses Slain";
		
		public BossDefeatedCondition() {
			super(DEFAULT_BOSS_KILL_COUNT, MIN_BOSS_KILL_COUNT, MAX_BOSS_KILL_COUNT, KEY_NAME, DISPLAY_STRING);
		}

		@Override
		public void invoke(GameData myGameData) {
			if (myGameData.getModifiablePlayerStatus().getStatusItemValue(KEY_NAME) >= getVal() && this.isEnabled()) {
				winGame(myGameData);
			}

		}

	}

