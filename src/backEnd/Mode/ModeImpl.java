package backEnd.Mode;

import java.util.Arrays;
import java.util.List;

import backEnd.LevelProgression.LevelProgressionControllerReader;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class contains the current mode and has getters/setters for people to access the mode
 * 
 * @author Riley
 *
 */
public class ModeImpl implements ModeReader, Mode{
	private String currUserMode;
	private String currGameMode;
	private String currLevelMode;
	private SimpleBooleanProperty aBP;
	private List<String> userModes;
	private List<String> gameModes;
	
	public ModeImpl(String userMode, String gameMode, String levelMode, LevelProgressionControllerReader levelProgression){
		this.currGameMode = gameMode;
		this.currUserMode = userMode;
		this.currLevelMode = levelMode;
		this.userModes = Arrays.asList("AUTHOR", "PLAYER");
		this.gameModes = levelProgression.getGameList();
		aBP = new SimpleBooleanProperty(this.getUserMode().equals("AUTHOR"));
	}

	@Override
	public void setUserMode(String newUserMode){
		currUserMode = newUserMode;
	}
	
	@Override
	public String getUserMode(){
		return currUserMode;
	}
	
	@Override
	public void setGameMode(String newGameMode){
		currGameMode = newGameMode;
	}
	
	@Override
	public String getGameMode(){
		return currGameMode;
	}
	
	@Override
	public String getGameModeString(){
		return currGameMode.toString();
	}
	
	@Override
	public void toggleUserMode(){
		System.out.println("MODE CHANGE");
		for (String mode : userModes){
			if (!mode.equals(currUserMode)){
				currUserMode = mode;
			}
		}
		aBP.setValue(!aBP.getValue());
	}
	
	@Override
	public SimpleBooleanProperty getAuthorBooleanProperty(){
		return aBP;
	}

	@Override
	public void setLevelMode(String newLevelMode) {
		currLevelMode = newLevelMode;
	}

	@Override
	public String getLevelMode() {
		return currLevelMode;
	}
	
}