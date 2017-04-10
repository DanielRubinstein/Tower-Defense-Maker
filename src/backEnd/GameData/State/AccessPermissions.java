package backEnd.GameData.State;

import backEnd.Mode.GameModeType;
import backEnd.Mode.UserModeType;

public interface AccessPermissions {

	/**
	 * Add a GameMode to access permission list
	 * @param gameMode
	 */
	void addAccessPermission(GameModeType gameMode);
	
	/**
	 * Add a UserMode to access permission list
	 * @param userMode
	 */
	void addAccessPermission(UserModeType userMode);
	
	/**
	 * @param gameMode
	 * @return boolean whether or not the GameMode has access
	 */
	boolean permitsAccess(GameModeType gameMode);
	
	/**
	 * @param gameMode
	 * @return boolean whether or not the UserMode has access
	 */
	boolean permitsAccess(UserModeType userMode);
}
