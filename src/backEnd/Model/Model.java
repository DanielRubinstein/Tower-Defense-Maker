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
	
	public Model(GameData gameData) {
		this.gameData = gameData;
		initializeGame(gameData);
	}

	private void initializeGame(GameData gameData){
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
