package backEnd.Mode;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class contains the current mode and has getters/setters for people to access the mode
 * 
 * @author Riley
 *
 */
public class ModeImpl implements ModeReader, Mode{
	private UserModeType currUserMode;
	private GameModeType currGameMode;
	private SimpleBooleanProperty aBP;
	
	public ModeImpl(){
		this(GameModeType.DEFAULT, UserModeType.AUTHOR);
	}
	
	public ModeImpl(GameModeType gameMode, UserModeType userMode){
		this.currGameMode = gameMode;
		this.currUserMode = userMode;
		aBP = new SimpleBooleanProperty(this.getUserModeString().equals("AUTHOR"));
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
		aBP.setValue(!aBP.getValue());
	}
	
	@Override
	public SimpleBooleanProperty getAuthorBooleanProperty(){
		return aBP;
	}
	
}