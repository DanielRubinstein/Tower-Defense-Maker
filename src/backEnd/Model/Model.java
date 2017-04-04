package backEnd.Model;

import java.util.Observable;

import backEnd.GameData;
import backEnd.Environment.Environment;
import backEnd.GameEngine.GameProcessController;
import backEnd.Mode.Author;
import backEnd.Mode.Mode;
import backEnd.State.State;

public class Model extends Observable{
	private GameData gameData;
	private GameProcessController engine;
	private Environment environment;
	private Mode mode;
	
	public void initializeGame(GameData gameData){
		this.gameData = gameData;
		engine = new GameProcessController(gameData.getState(), gameData.getRules());
		environment = new Environment();
		mode = new Author();
	}
	
	public void setChangedAndNotifyObservers() {
		setChanged();
		notifyObservers();
	}
	
	public State getState(){
		return this.gameData.getState();
	}

}
