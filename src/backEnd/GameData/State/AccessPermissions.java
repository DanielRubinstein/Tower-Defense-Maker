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
	
	/**
	 * @param gameMode
	 * @return boolean whether or not the GameMode has access
	 */
	boolean permitsAccess(String mode);
	
	List<String> getGameModeList();
	
	List<String> getUserModeList();
}
