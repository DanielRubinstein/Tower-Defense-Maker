package backEnd.Mode;

import java.util.List;

public interface Mode {

	/**
	 * Choose User Mode
	 * @param newUserMode
	 */
	void setUserMode(String newUserMode);

	/**
	 * @return current User Mode
	 */
	String getUserMode();

	/**
	 * Choose Game Mode
	 * @param newGameMode
	 */
	void setGameMode(String newGameMode);

	/**
	 * @return current Game Mode
	 */
	String getGameMode();
	
	/**
	 * Choose Level Mode
	 * @param newLevelMode
	 */
	void setLevelMode(String newLevelMode);

	/**
	 * @return current Level Mode
	 */
	String getLevelMode();
	
	/**
	 * @return comprehensive of User Modes
	 */
	List<String> getAllUserModes();

	/**
	 * Change User Mode (Author to Player or Player to Author)
	 */
	void toggleUserMode();

}