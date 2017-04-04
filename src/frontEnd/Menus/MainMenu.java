package frontEnd.Menus;

import java.util.function.Consumer;

import backEnd.GameData;
import backEnd.Data.GameFileException;
import frontEnd.Skeleton.View.Skeleton;
import javafx.stage.Stage;

/**
 * The main menu which allows the use to
 * 	1) Create a new game (via GameMaker)
 *  2) Open a Game Template
 *  3) Load a saved game
 * @author Miguel Anderson
 *
 */

public class MainMenu implements StartMenu{
	private Consumer<GameData> gameDataConsumer;
	public static final double MENU_HEIGHT = 500d;
	public static final double MENU_WIDTH = 600d;
	

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
   	 	splash.addButton("Quick start (for development purposes)", event -> {
   	 		Skeleton view = new Skeleton();
   	 		view.display(stage);
   	 	});
   	 	splash.create(MENU_WIDTH, MENU_HEIGHT);
		stage.setScene(splash.getScene());
		stage.show();
	}

	private void showPrimaryMenu(Stage stage) {
		ButtonMenu primaryMenu = new ButtonMenu();
   	 	makeGameSelectionButtons(primaryMenu,stage);
   	 	primaryMenu.addBackButton(event -> splashScreen(stage));
   	 	primaryMenu.create(MENU_WIDTH, MENU_HEIGHT);
		stage.setScene(primaryMenu.getScene());
		stage.show();
	}
	public void makeGameSelectionButtons(ButtonMenu menu,Stage stage){
		menu.setText("Please Pick one");
   	 	menu.addButton("Create a New Game", event -> new GameMaker(stage, gameDataConsumer));
   	 	menu.addButton("Load a Template Game", event -> showTemplateMenu(stage));
   	 	menu.addButton("Load a Saved Game", event -> loadGame());
		
		
	}
	

	private void loadGame() {
		GameLoader gameLoader = new GameLoader();
		try {
			GameData loadedGameData = gameLoader.loadGame();
			gameDataConsumer.accept(loadedGameData);
		} catch (GameFileException e) {
			ErrorDialog errDia = new ErrorDialog();
			errDia.create("Cannot Load Game", e.getMessage());
		}
		
	}

	private void showTemplateMenu(Stage stage) {
		GameLoader gameLoader = new GameLoader();
		
		ButtonMenu templateGames = new ButtonMenu();
   	 	templateGames.setText("Which game?");
   	 	//for(String templateGame : gameLoader.getTemplateTitleList()){
   	 	for(String templateGame : gameLoader.getTemplateTitleListStupid()){
   	 		templateGames.addButton(templateGame, event -> gameDataConsumer.accept(gameLoader.loadTemplateGame(templateGame)));
   	 	}
   	 	templateGames.addBackButton(event -> showPrimaryMenu(stage));
   	 	templateGames.create(MENU_WIDTH, MENU_HEIGHT);
		stage.setScene(templateGames.getScene());
		stage.show();
	}




}
