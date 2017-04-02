package frontEnd.Menus;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import backEnd.GameData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * The main menu which allows the use to
 * 	1) Create a new game (via GameMaker)
 *  2) Open a predefined Game Template
 *  3) Load a saved game
 * @author Miguel Anderson
 *
 */

public class MainMenu {
	private Consumer<GameData> gameDataConsumer;
	public static final double MENU_HEIGHT = 500d;
	public static final double MENU_WIDTH = 600d;
	
	private List<String> predefinedGameTitleList = Arrays.asList(new String[] { "Bloons Tower Defense", "Plants vs. Zombies", "Desktop Tower Defense" });;
	
	public void setGameDataListener(Consumer<GameData> action){
		this.gameDataConsumer = action;
	}

	public void showMenus(Stage stage) {
		splashScreen(stage);
	}
	
	private void splashScreen(Stage stage) {
   	 	ButtonMenu splash = new ButtonMenu();
   	 	splash.setText("WELCOME");
   	 	splash.addButton("START", event -> showPrimaryMenu(stage));
   	 	splash.create(MENU_WIDTH, MENU_HEIGHT);
		stage.setScene(splash.getScene());
		stage.show();
	}

	private void showPrimaryMenu(Stage stage) {
		
		
		ButtonMenu primaryMenu = new ButtonMenu();
   	 	primaryMenu.setText("Please Pick one");
   	 	primaryMenu.addButton("Create a New Game", event -> new GameMaker(stage, gameDataConsumer));
   	 	primaryMenu.addButton("Start a Predefined Game", event -> showPredefinedMenu(stage));
   	 	primaryMenu.addButton("Load a Saved Game", event -> loadGame());
   	 	primaryMenu.create(MENU_WIDTH, MENU_HEIGHT);
		stage.setScene(primaryMenu.getScene());
		stage.show();
	}

	private void loadGame() {
		GameLoader gameLoader = new GameLoader();
		GameData loadedGameData = gameLoader.loadGame();
		gameDataConsumer.accept(loadedGameData);
	}

	private void showPredefinedMenu(Stage stage) {
		GameLoader gameLoader = new GameLoader();
		
		ButtonMenu predefinedGames = new ButtonMenu();
   	 	predefinedGames.setText("Which game?");
   	 	for(String predefinedGame : predefinedGameTitleList){
   	 		predefinedGames.addButton(predefinedGame, event -> gameDataConsumer.accept(gameLoader.loadPredefinedGame(predefinedGame)));
   	 	}
   	 	predefinedGames.create(MENU_WIDTH, MENU_HEIGHT);
		stage.setScene(predefinedGames.getScene());
		stage.show();
	}
	
	

}
