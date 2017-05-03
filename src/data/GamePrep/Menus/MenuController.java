package data.GamePrep.Menus;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import data.GamePrep.GameMaker;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Facebook.FacebookInteractor;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

/**
 * The main menu which allows the use to
 * 	1) Create a new game (via GameMaker)
 *  2) Open a Game Template
 *  3) Load a saved game
 * @author Miguel Anderson
 *
 */

public class MenuController{
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
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
		stage.getIcons().add(new Image(stringResourceBundle.getFromStringConstants("LOGO")));
		MenuSplash menuSplash = new MenuSplash(stage, (buttonMenu) -> showPrimaryGameMenu(buttonMenu, stage), setFb);
		menuSplash.display();
	}

	private void showPrimaryGameMenu(ButtonMenuImpl previousMenu, Stage stage) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl(stringResourceBundle.getFromMenuText("GameMenuTitle"));
		primaryMenu.addSimpleButtonWithHover(stringResourceBundle.getFromMenuText("CreateButton"), () -> {
			showNewGameMenu(primaryMenu, stage);
		}, stringResourceBundle.getFromMenuText("CreateButtonHover"));
		primaryMenu.addSimpleButtonWithHover(stringResourceBundle.getFromMenuText("SelectButton"), () -> {
			MenuSelectedGame menuSelectedGame = new MenuSelectedGame(primaryMenu, stage, consumerLoadData);
			menuSelectedGame.display();
		}, stringResourceBundle.getFromMenuText("SelectButtonHover"));
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
		List<String> dialogTitles = Arrays.asList(stringResourceBundle.getFromStringConstants("Welcome")
				, stringResourceBundle.getFromMenuText("InputName"));
		String promptLabel = stringResourceBundle.getFromMenuText("NamePrefix");
		String promptText = "";
		SingleFieldPrompt myDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
		return myDialog.getUserInputString();
	}

}
