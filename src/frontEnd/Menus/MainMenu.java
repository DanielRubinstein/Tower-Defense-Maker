package frontEnd.Menus;

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
	public static final double MENU_HEIGHT = 500d;
	public static final double MENU_WIDTH = 600d;
	
	private GameSelection gameSelection;

	public void showMenus(Stage stage) {
		splashScreen(stage);
	}
	
	private void splashScreen(Stage stage) {
   	 	ButtonMenu splash = new ButtonMenu();
   	 	splash.setText("WELCOME");
   	 	splash.addSimpleButton("START", event -> showPrimaryMenu(stage));
   	 	splash.create(MENU_WIDTH, MENU_HEIGHT);
		stage.setScene(splash.getScene());
		stage.show();
	}

	private void showPrimaryMenu(Stage stage) {
		ButtonMenu primaryMenu = new ButtonMenu();
   	 	for( Button button : gameSelection.getButtons()){
   	 		primaryMenu.addButton(button);
   	 	}
   	 	primaryMenu.addBackButton(event -> splashScreen(stage));
   	 	primaryMenu.create(MENU_WIDTH, MENU_HEIGHT);
		stage.setScene(primaryMenu.getScene());
		stage.show();
	}

	public void setGameSelection(GameSelection gameSelection) {
		this.gameSelection = gameSelection;
		
	}

}
