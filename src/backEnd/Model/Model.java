package backEnd.Model;

import java.util.Observable;

import backEnd.GameData;
import backEnd.Environment.Environment;
import backEnd.Environment.EnvironmentInterface;
import backEnd.GameEngine.GameProcessController;
import backEnd.Mode.Author;
import backEnd.Mode.Mode;
import backEnd.State.State;
import backEnd.State.StateImpl;
import frontEnd.ViewEditor;

public class Model extends Observable{
	private GameData gameData;
	private GameProcessController engine;
	private Environment environment;
	private EnvironmentInterface myEnvironment;
	private State myState;
	private GameProcessController myEngineController;
	private Mode mode;
	
	public Model(GameData gameData) {
		this.gameData = gameData;
		//initializeGame(gameData);
	}

	private void initializeGame(GameData gameData){
		myEnvironment = new Environment();
		myState = new StateImpl(20,20);
		myEngineController = new GameProcessController(myState, null); //this will be gameData.getRules, not null
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
