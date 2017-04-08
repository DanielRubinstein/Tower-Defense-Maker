package backEnd.Mode;


/**
 * This class contains the current mode and has getters/setters for people to access the mode
 * 
 * @author Riley
 *
 */
public class Mode{
	private UserModeType currUserMode;
	private GameModeType currGameMode;
	
	public Mode(GameModeType gameMode, UserModeType userMode){
		this.currGameMode = gameMode;
		this.currUserMode = userMode;
	}

	public void setUserMode(UserModeType newUserMode){
		currUserMode = newUserMode;
	}
	
	public UserModeType getUserMode(){
		return currUserMode;
	}
	
	public String getUserModeString(){
		return currUserMode.toString();
	}
	
	public void setGameMode(GameModeType newGameMode){
		currGameMode = newGameMode;
	}
	
	public GameModeType getGameMode(){
		return currGameMode;
	}
	
	public String getGameModeString(){
		return currGameMode.toString();
	}
	
}