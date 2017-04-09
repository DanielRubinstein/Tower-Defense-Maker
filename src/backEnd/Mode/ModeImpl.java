package backEnd.Mode;


/**
 * This class contains the current mode and has getters/setters for people to access the mode
 * 
 * @author Riley
 *
 */
public class ModeImpl implements ModeReader, Mode{
	private UserModeType currUserMode;
	private GameModeType currGameMode;
	
	public ModeImpl(){
		this.currGameMode = GameModeType.DEFAULT;
		this.currUserMode = UserModeType.AUTHOR;
	}
	
	public ModeImpl(GameModeType gameMode, UserModeType userMode){
		this.currGameMode = gameMode;
		this.currUserMode = userMode;
	}

	@Override
	public void setUserMode(UserModeType newUserMode){
		currUserMode = newUserMode;
	}
	
	@Override
	public UserModeType getUserMode(){
		return currUserMode;
	}
	
	@Override
	public String getUserModeString(){
		return currUserMode.toString();
	}
	
	@Override
	public void setGameMode(GameModeType newGameMode){
		currGameMode = newGameMode;
	}
	
	@Override
	public GameModeType getGameMode(){
		return currGameMode;
	}
	
	@Override
	public String getGameModeString(){
		return currGameMode.toString();
	}
	
	@Override
	public void toggleUserMode(){
		System.out.println("MODE CHANGE");
		currUserMode = UserModeType.getNextMode(currUserMode);
	}
	
}