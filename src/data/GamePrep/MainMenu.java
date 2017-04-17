package data.GamePrep;

import java.io.File;
import java.util.function.Consumer;

import data.XMLReadingException;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.stage.Stage;

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
	
	public MainMenu(Consumer<Object> loadData){
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
		primaryMenu.display(stage);
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
