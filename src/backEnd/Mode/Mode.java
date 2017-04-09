package backEnd.Mode;

public interface Mode {

	/**
	 * Choose User Mode
	 * @param newUserMode
	 */
	void setUserMode(UserModeType newUserMode);

	/**
	 * @return current User Mode
	 */
	UserModeType getUserMode();

	/**
	 * @return current User Mode as string
	 */
	String getUserModeString();

	/**
	 * Choose Game Mode
	 * @param newGameMode
	 */
	void setGameMode(GameModeType newGameMode);

	/**
	 * @return current Game Mode
	 */
	GameModeType getGameMode();

	/**
	 * @return current Game Mode as string
	 */
	String getGameModeString();

	/**
	 * Change User Mode (Author to Player or Player to Author)
	 */
	void toggleUserMode();

}