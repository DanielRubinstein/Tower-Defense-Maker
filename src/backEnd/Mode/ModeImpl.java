package backEnd.Mode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import backEnd.GameData.State.SerializableObserver;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import resources.constants.StringResourceBundle;

/**
 * This class contains the current mode and has getters/setters for people to access the mode
 * 
 * @author Riley
 *
 */
public class ModeImpl implements ModeReader, Mode{
	private static final StringResourceBundle strResources = new StringResourceBundle();
	private String currUserMode;
	private String currGameMode;
	private String currLevelMode;
	private SimpleBooleanProperty aBP;
	private SimpleStringProperty gSP;
	private SimpleStringProperty lSP;
	private List<String> userModes;
	private List<SerializableObserver> observers;
	private LevelProgressionControllerReader levelProgression;
	
	public ModeImpl(String userMode, String gameMode, String levelMode, LevelProgressionControllerReader levelProgression){
		this.currGameMode = gameMode;
		this.currUserMode = userMode;
		this.currLevelMode = levelMode;
		this.levelProgression = levelProgression;
		this.userModes = Arrays.asList(strResources.getFromStringConstants("AUTHOR"), strResources.getFromStringConstants("PLAYER"));
		this.observers = new ArrayList<SerializableObserver>();
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
		if (!levelProgression.getLevelList(newGameMode).contains(newGameMode)){
			currLevelMode = strResources.getFromStringConstants("DEFAULT");
		}
		notifyObservers();
	}
	
	@Override
	public String getGameMode(){
		return currGameMode;
	}
	
	@Override
	public void toggleUserMode(){
		System.out.println("MODE CHANGE");
		if(currUserMode.equals(strResources.getFromStringConstants("AUTHOR"))){
			currUserMode = strResources.getFromStringConstants("PLAYER");
		} else if (currUserMode.equals(strResources.getFromStringConstants("PLAYER"))){
			currUserMode = strResources.getFromStringConstants("AUTHOR");
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
		
		notifyObservers();
	}

	@Override
	public String getLevelMode() {
		return currLevelMode;
	}

	@Override
	public void addObserver(SerializableObserver o) {
		observers.add(o);
	}
	
	private void notifyObservers() {
		System.out.println("Notifying Observers within ModeImpl (i.e. resetting Palette)");
		for(SerializableObserver o : observers){
			o.update(null, null);
		}
	}
	
}