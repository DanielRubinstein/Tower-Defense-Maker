package backEnd.Mode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observer;

import backEnd.LevelProgression.LevelProgressionControllerReader;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

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
	private SimpleStringProperty gSP;
	private SimpleStringProperty lSP;
	private List<String> userModes;
	private List<Observer> observers;
	private LevelProgressionControllerReader levelProgression;
	
	public ModeImpl(String userMode, String gameMode, String levelMode, LevelProgressionControllerReader levelProgression){
		this.currGameMode = gameMode;
		this.currUserMode = userMode;
		this.currLevelMode = levelMode;
		this.levelProgression = levelProgression;
		this.userModes = Arrays.asList("AUTHOR", "PLAYER");
		this.observers = new ArrayList<Observer>();
		aBP = new SimpleBooleanProperty(this.getUserMode().equals("AUTHOR"));
		gSP = new SimpleStringProperty(this.getGameMode());
		lSP = new SimpleStringProperty(this.getLevelMode());
	}

	@Override
	public void setUserMode(String newUserMode){
		currUserMode = newUserMode;
		notifyObservers();
	}

	@Override
	public String getUserMode(){
		return currUserMode;
	}
	
	@Override
	public List<String> getAllUserModes(){
		return userModes;
	}
	
	@Override
	public void setGameMode(String newGameMode){
		currGameMode = newGameMode;
		currLevelMode = "DEFAULT";
		notifyObservers();
	}
	
	@Override
	public String getGameMode(){
		return currGameMode;
	}
	
	@Override
	public void toggleUserMode(){
		System.out.println("MODE CHANGE");
		if(currUserMode.equals("AUTHOR")){
			currUserMode = "PLAYER";
		} else if (currUserMode.equals("PLAYER")){
			currUserMode = "AUTHOR";
		} 
		aBP.setValue(!aBP.getValue());
		notifyObservers();
	}
	
	@Override
	public SimpleBooleanProperty getAuthorBooleanProperty(){
		return aBP;
	}
	
	@Override
	public SimpleStringProperty getGameStringProperty(){
		return gSP;
	}
	
	@Override
	public SimpleStringProperty getLevelStringProperty(){
		return lSP;
	}

	@Override
	public void setLevelMode(String newLevelMode) {
		currLevelMode = newLevelMode;
		for (String gameName : levelProgression.getGameList()){
			if (levelProgression.getLevelList(gameName).contains(newLevelMode)){
				currGameMode = gameName;
				break;
			}
		}
		notifyObservers();
	}

	@Override
	public String getLevelMode() {
		return currLevelMode;
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}
	
	private void notifyObservers() {
		System.out.println("Notifying Observers within ModeImpl (i.e. resetting Palette)");
		for(Observer o : observers){
			o.update(null, null);
		}
	}
	
}