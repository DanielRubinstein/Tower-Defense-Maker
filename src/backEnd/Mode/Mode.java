package backEnd.Mode;

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
	 * Change User Mode (Author to Player or Player to Author)
	 */
	void toggleUserMode();

}