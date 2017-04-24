package backEnd.LevelProgression;

import java.util.List;

public interface LevelProgressionControllerEditor extends LevelProgressionControllerReader{

	void setLevelList(String gameName, List<String> levelList);

	void addNewGame(String gameName);

	void addLevelToGame(String gameName, String level);

	void removeGame(String gameName);

	void removeLevelFromGame(String gameName, String level);

	void saveGamesMap();

}