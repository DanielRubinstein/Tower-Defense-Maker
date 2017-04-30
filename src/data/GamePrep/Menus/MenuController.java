package data.GamePrep.Menus;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import data.GamePrep.GameMaker;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Facebook.FacebookInteractor;
import javafx.stage.Stage;

/**
 * The main menu which allows the use to
 * 	1) Create a new game (via GameMaker)
 *  2) Open a Game Template
 *  3) Load a saved game
 * @author Miguel Anderson, Juan Philippe
 *
 */

public class MenuController{
	private Consumer<Object> consumerLoadData;
	private Consumer<FacebookInteractor> setFb;
	
	public MenuController(Consumer<Object> setGameData, Consumer<FacebookInteractor> setFb){
		consumerLoadData = setGameData;
		this.setFb = setFb;
	}

	public void showMenus(Stage stage) {
		splashScreen(stage);
	}
	
	private void splashScreen(Stage stage) {
		MenuSplash menuSplash = new MenuSplash(stage, (buttonMenu) -> showPrimaryGameMenu(buttonMenu, stage), setFb);
		menuSplash.display();
	}

	private void showPrimaryGameMenu(ButtonMenuImpl previousMenu, Stage stage) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Game Menu");
		primaryMenu.addSimpleButtonWithHover("Create a New Game", () -> {
			showNewGameMenu(primaryMenu, stage);
		}, "Create a new game from stratch, choose everything!!");
		primaryMenu.addSimpleButtonWithHover("Select Game", () -> {
			MenuSelectedGame menuSelectedGame = new MenuSelectedGame(primaryMenu, stage, consumerLoadData);
			menuSelectedGame.display();
		}, "Select from already-made games. From here you can load a game, make a level or start from the beginning");
		primaryMenu.addBackButton(previousMenu, stage);
		primaryMenu.display(stage);
	}
	
	private void showNewGameMenu(ButtonMenuImpl previousMenu, Stage stage){
		String newGameName = getNewGameName();
		if(newGameName != null){
			GameMaker gameMaker = new GameMaker(stage, consumerLoadData, newGameName, previousMenu);
			gameMaker.display();
		}
	}
	
	private String getNewGameName() {
		List<String> dialogTitles = Arrays.asList("Welcome!", "Please Input a Name for your new game");
		String promptLabel = "Name:";
		String promptText = "";
		SingleFieldPrompt myDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
		return myDialog.getUserInputString();
	}

}
