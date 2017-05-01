package frontEnd.Skeleton.SplashScreens;

import backEnd.GameData.State.PlayerStatusReader;

public interface SplashScreen extends SplashScreenReader {
	
	void addRestartLevelButton();
	
	void setStatusDisplayValues(PlayerStatusReader playerStatus);
}
