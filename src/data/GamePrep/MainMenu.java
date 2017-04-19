package data.GamePrep;

import java.io.File;
import java.util.function.Consumer;

import data.XMLReadingException;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Facebook.FacebookConnectorImpl;
import frontEnd.Facebook.FacebookInteractor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.ErrorDialog;
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
   	 	splash.addPrimarySimpleButtonWithHover("START", () -> showPrimaryMenu(stage), "Click to start the game!");
		splash.display(stage);
	}

	private void showPrimaryMenu(Stage stage) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Games");
   	 	primaryMenu.addPrimarySimpleButtonWithHover("New Game", () -> new GameMaker(stage, consumerLoadData), "Create A New Game after selecting the size of the screen");
   	 	primaryMenu.addSimpleButtonWithHover("Load Template Game", () -> showTemplateMenu(stage), "Load a game from a list of preapproved, ready-to-play templates");
   	 	primaryMenu.addSimpleButtonWithHover("Load Saved Game", () -> loadGame(), "Continue your progress by loading a user-saved game");
   	 	primaryMenu.addSimpleButtonWithHover("Go Back", () -> splashScreen(stage), "Return to previous screen");
   	 	primaryMenu.addSimpleButtonWithHover("Connect To Facebook", () -> launchFb(stage), "Log in and connect to Facebook to see high scores, screenshots, post to the official voogasalad_su3ps1ckt34m1337 page");
		primaryMenu.display(stage);
	}

	
	private void launchFb(Stage stage) {
		Stage loginStage = new Stage();
		loginStage.initOwner(stage);
		loginStage.initModality(Modality.APPLICATION_MODAL);

		FacebookConnector fb = new FacebookConnectorImpl("426668214360430","d97cba98608128cdb4ca19e1da091de5");
		ButtonMenuImpl myLoginButton = new ButtonMenuImpl("Login!");
		myLoginButton.addPrimarySimpleButtonWithHover("Login", () -> {
			fb.login();
			FacebookInteractor fbInter= fb.getInteractor();
			myController.setFb(fbInter);
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
   	 		templateGames.addSimpleButton(templateGame, () -> {
   	 			consumerLoadData.accept(templateGame);
   	 		});
   	 	}
   	 	templateGames.addSimpleButtonWithHover("Go Back", () -> showPrimaryMenu(stage), "Return to previous screen");
   	 	templateGames.display(stage);
	}

}
