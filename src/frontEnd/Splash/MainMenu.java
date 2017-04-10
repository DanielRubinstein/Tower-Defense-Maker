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
	private Consumer<StartingInput> consumerEnterData;
	private Consumer<File> consumerLoadData;
	
	public MainMenu(Consumer<File> loadData,Consumer<StartingInput> enterData){
		consumerEnterData = enterData;
		consumerLoadData = loadData;
	}

	public void showMenus(Stage stage) {
		splashScreen(stage);
	}
	
	private void splashScreen(Stage stage) {
   	 	ButtonMenuImpl splash = new ButtonMenuImpl("Welcome");
   	 	splash.addSimpleButton("START", event -> showPrimaryMenu(stage));
		splash.display(stage);
	}

	private void showPrimaryMenu(Stage stage) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Games");
		primaryMenu.setText("Yo");
   	 	primaryMenu.addSimpleButton("New Game", e -> new GameMaker(stage, consumerEnterData));
   	 	primaryMenu.addSimpleButton("Load Template Game", e -> showTemplateMenu(new Stage()));
   	 	primaryMenu.addSimpleButton("Load Saved Game", e-> loadGame());
   	 	primaryMenu.addBackButton(event -> splashScreen(stage));
		primaryMenu.display(stage);
	}

	
	private void loadGame() {
		GameLoader gameLoader = new GameLoader();
		try {
			GameData loadedGameData = gameLoader.loadGame();
			//gameDataConsumer.accept(loadedGameData);
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
   	 			//gameDataConsumer.accept(gameLoader.loadTemplateGame(templateGame));
   	 			
   	 		});
   	 	}
		templateGames.display(stage);
	}

}
