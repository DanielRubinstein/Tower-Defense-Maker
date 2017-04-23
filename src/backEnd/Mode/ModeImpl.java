package backEnd.Mode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observer;

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
	private List<Observer> observers;
	
	public ModeImpl(String userMode, String gameMode, String levelMode, LevelProgressionControllerReader levelProgression){
		this.currGameMode = gameMode;
		this.currUserMode = userMode;
		this.currLevelMode = levelMode;
		this.userModes = Arrays.asList("AUTHOR", "PLAYER");
		this.observers = new ArrayList<Observer>();
		aBP = new SimpleBooleanProperty(this.getUserMode().equals("AUTHOR"));
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
	public void setLevelMode(String newLevelMode) {
		currLevelMode = newLevelMode;
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
		System.out.println("notifying observersssssss2");
		for(Observer o : observers){
			o.update(null, null);
		}
	}
	
}