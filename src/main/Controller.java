package main;

import backEnd.GameData;
import backEnd.Environment.Environment;
import backEnd.GameEngine.Engine;
import frontEnd.Skeleton;
import javafx.stage.Stage;

public class Controller implements ControllerInterface{
	
	public void start(Stage stage){
		Skeleton skeleton = new Skeleton();
		
		GameData gameData = skeleton.getGameData();
		
		initializeGame(gameData);
		
		
	}

	private void initializeGame(GameData gameData) {
		//Engine engine = new Engine();
		//engine.setRules(gameData.getRules());
		//Environment environment = new Environment();
		//environment.setState(gameData.getState());
		
		
	}

}
