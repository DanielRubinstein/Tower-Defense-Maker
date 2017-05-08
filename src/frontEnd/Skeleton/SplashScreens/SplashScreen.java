package frontEnd.Skeleton.SplashScreens;

import backEnd.GameData.State.PlayerStatusReader;

/**
 * @author Derek
 *
 */
public interface SplashScreen extends SplashScreenReader {
	
	void addRestartLevelButton();
	
	void setStatusDisplayValues(PlayerStatusReader playerStatus);
}
