package data.GamePrep;

import java.io.File;
import java.util.function.Consumer;

import data.XMLReadingException;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Facebook.FacebookConnectorImpl;
import frontEnd.Menus.ButtonMenuImpl;
import frontEnd.Menus.ErrorDialog;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Controller;

/**
 * The main menu which allows the use to
 * 	1) Create a new game (via GameMaker)
 *  2) Open a Game Template
 *  3) Load a saved game
 * @author Miguel Anderson
 *
 */

public class MainMenu{
	private Consumer<Object> consumerLoadData;
	private Controller myController;
	
	public MainMenu(Consumer<Object> loadData,Controller con){
		myController = con;
		consumerLoadData = loadData;
	}

	public void showMenus(Stage stage) {
		splashScreen(stage);
	}
	
	private void splashScreen(Stage stage) {
   	 	ButtonMenuImpl splash = new ButtonMenuImpl("Welcome");
   	 	splash.addSimpleButtonWithHover("START", event -> showPrimaryMenu(stage), "Click to start the game!");
		splash.display(stage);
	}

	private void showPrimaryMenu(Stage stage) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Games");
   	 	primaryMenu.addSimpleButtonWithHover("New Game", e -> new GameMaker(stage, consumerLoadData), "Create A New Game after selecting the size of the screen");
   	 	primaryMenu.addSimpleButtonWithHover("Load Template Game", e -> showTemplateMenu(stage), "Load a game from a list of preapproved, ready-to-play templates");
   	 	primaryMenu.addSimpleButtonWithHover("Load Saved Game", e-> loadGame(), "Continue your progress by loading a user-saved game");
   	 	primaryMenu.addSimpleButtonWithHover("Go Back", event -> splashScreen(stage), "Return to previous screen");
   	 	primaryMenu.addSimpleButtonWithHover("Connect To Facebook", e -> launchFb(stage), "Log in and connect to Facebook to see high scores, screenshots, post to the official voogasalad_su3ps1ckt34m1337 page");
		primaryMenu.display(stage);
	}

	
	private void launchFb(Stage stage) {
		Stage loginStage = new Stage();
		loginStage.initOwner(stage);
		loginStage.initModality(Modality.APPLICATION_MODAL);

		FacebookConnector fb = new FacebookConnectorImpl();
		ButtonMenuImpl myLoginButton = new ButtonMenuImpl("Login!");
		myLoginButton.addSimpleButtonWithHover("Login", e -> {
			fb.login();
			myController.setFb(fb);
			loginStage.close();
		}, "Click to launch facebook");
		myLoginButton.display(loginStage);
	}

	private void loadGame() {
		GameLoader gameLoader = new GameLoader();
		try {
			File loadedGame = gameLoader.loadGame();
			consumerLoadData.accept(loadedGame);
		} catch (XMLReadingException e) {
			ErrorDialog errDia = new ErrorDialog();
			errDia.create("Cannot Load Game", e.getMessage());
		}
		
	}
	
	private void showTemplateMenu(Stage stage) {
		GameLoader gameLoader = new GameLoader();
		
		ButtonMenuImpl templateGames = new ButtonMenuImpl("Templates");
   	 	//for(String templateGame : gameLoader.getTemplateTitleList()){
   	 	for(String templateGame : gameLoader.getTemplateTitleListStupid()){
   	 		templateGames.addSimpleButton(templateGame, event -> {
   	 			consumerLoadData.accept(templateGame);
   	 		});
   	 	}
   	 	templateGames.addSimpleButtonWithHover("Go Back", event -> showPrimaryMenu(stage), "Return to previous screen");
   	 	templateGames.display(stage);
	}

}
