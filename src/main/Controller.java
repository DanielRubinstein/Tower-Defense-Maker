package main;

import backEnd.GameData;
import backEnd.Environment.Environment;
import backEnd.GameEngine.Engine;
import frontEnd.Menus.MainMenu;
import javafx.stage.Stage;

public class Controller implements ControllerInterface{
	
	public void start(Stage stage){
		openingScreens(stage);
	}

	private void openingScreens(Stage stage) {
		MainMenu mainMenu = new MainMenu();
		mainMenu.setGameDataListener(this::initializeGame);
		mainMenu.showMenus(stage);
	}

	private void initializeGame(GameData gameData) {
		//Engine engine = new Engine();
		//engine.setRules(gameData.getRules());
		//Environment environment = new Environment();
		//environment.setState(gameData.getState());
		
		
	}

}
