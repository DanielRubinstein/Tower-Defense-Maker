package backEnd.LevelProgression;

import java.util.List;

import frontEnd.Skeleton.SplashScreens.SplashScreenData;

public interface LevelProgressionControllerEditor extends LevelProgressionControllerReader{

	void setLevelList(String gameName, List<String> levelList);

	void addNewGame(String gameName);

	void addLevelToGame(String gameName, String level);

	void removeGame(String gameName);

	void removeLevelFromGame(String gameName, String level);

	void saveGamesMap();

	void initiateSplashScreen(SplashScreenData splashScreenData);

}