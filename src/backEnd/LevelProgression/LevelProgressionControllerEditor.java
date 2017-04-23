package backEnd.LevelProgression;

import java.util.List;

public interface LevelProgressionControllerEditor {

	/* (non-Javadoc)
	 * @see backEnd.LevelProgression.LevelProgressionControllerReader#getGameList()
	 */
	List<String> getGameList();

	/* (non-Javadoc)
	 * @see backEnd.LevelProgression.LevelProgressionControllerReader#getLevelList(java.lang.String)
	 */
	List<String> getLevelList(String gameName);

	List<String> getFullLevelList();

	void setLevelList(String gameName, List<String> levelList);

	void addNewGame(String gameName);

	void addLevelToGame(String gameName, String level);

	void removeGame(String gameName);

	void removeLevelFromGame(String gameName, String level);

}