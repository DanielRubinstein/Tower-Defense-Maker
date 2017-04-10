package frontEnd.Splash;

import java.io.File;
import java.util.function.Consumer;

import backEnd.Data.XMLReadingException;
import backEnd.GameData.GameData;
import frontEnd.Menus.ButtonMenuImpl;
import frontEnd.Menus.ErrorDialog;
import javafx.scene.control.Button;
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
   	 	splash.addSimpleButtonWithHover("START", event -> showPrimaryMenu(stage), "Click to start the game!");
		splash.display(stage);
	}

	private void showPrimaryMenu(Stage stage) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Games");
   	 	primaryMenu.addSimpleButtonWithHover("New Game", e -> new GameMaker(stage, consumerLoadData), "Create A New Game after selecting the size of the screen");
   	 	primaryMenu.addSimpleButtonWithHover("Load Template Game", e -> showTemplateMenu(new Stage()), "Load a game from a list of preapproved, ready-to-play templates");
   	 	primaryMenu.addSimpleButtonWithHover("Load Saved Game", e-> loadGame(), "Continue your progress by loading a user-saved game");
   	 	primaryMenu.addSimpleButtonWithHover("Go Back", event -> splashScreen(stage), "Return to previous screen");
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
   	 	templateGames.setText("Which game?");
   	 	//for(String templateGame : gameLoader.getTemplateTitleList()){
   	 	for(String templateGame : gameLoader.getTemplateTitleListStupid()){
   	 		templateGames.addSimpleButton(templateGame, event -> {
   	 			consumerLoadData.accept(templateGame);
   	 		});
   	 	}
		templateGames.display(stage);
	}

}
