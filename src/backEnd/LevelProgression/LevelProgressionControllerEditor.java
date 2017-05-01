package backEnd.LevelProgression;

import java.util.List;

import backEnd.GameData.State.PlayerStatusReader;
import frontEnd.Skeleton.SplashScreens.SplashScreenReader;
import frontEnd.Skeleton.SplashScreens.SplashScreenType;

public interface LevelProgressionControllerEditor extends LevelProgressionControllerReader{

	void setLevelList(String gameName, List<String> levelList);

	void addNewGame(String gameName);

	void addLevelToGame(String gameName, String level);

	void removeGame(String gameName);

	void removeLevelFromGame(String gameName, String level);

	void saveGamesMap();

	void initiateSplashScreen(SplashScreenType type, PlayerStatusReader playerStatus);

	void loadNextLevel();

	void addLevelToCurrentGame(String newName);
	
	boolean existsNextLevel();
	
	void reloadLevel();
	
	void loadFirstLevel();
	
}