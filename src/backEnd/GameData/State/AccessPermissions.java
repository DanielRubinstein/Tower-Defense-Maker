package backEnd.GameData.State;

import java.util.List;


public interface AccessPermissions {

	/**
	 * Add a GameMode to access permission list
	 * @param gameMode
	 */
	void addUserAccessPermission(String gameMode);
	
	/**
	 * Add a UserMode to access permission list
	 * @param userMode
	 */
	void addGameAccessPermission(String userMode);
	
	void addLevelAccessPermission(String levelMode);
	
	void removeUserAccessPermission(String gameMode);

	void removeGameAccessPermission(String gameMode);

	void removeLevelAccessPermission(String levelMode);
	
	/**
	 * @param gameMode
	 * @return boolean whether or not the GameMode has access
	 */
	boolean permitsAccess(String userMode, String gameMode, String levelMode);
	
	List<String> getGameModeList();
	
	List<String> getUserModeList();

	List<String> getLevelModeList();

}
