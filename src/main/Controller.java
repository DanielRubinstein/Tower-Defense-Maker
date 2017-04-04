package main;

import backEnd.Model.Model;
import frontEnd.View;
import frontEnd.Menus.MainMenu;
import javafx.stage.Stage;

public class Controller implements ControllerInterface{
	private Model myModel;
	private View myView;
	
	public void start(Stage stage){
		setupModelViewBridge();
		openingScreens(stage);
	}
	private void openingScreens(Stage stage) {
		MainMenu mainMenu = new MainMenu();
		mainMenu.setGameDataListener(gameData -> myModel.initializeGame(gameData));
		mainMenu.showMenus(stage);
	}

	private void setupModelViewBridge() {
		myModel = new Model();
		myView = new View();
		myModel.addObserver(myView);
	}


}
