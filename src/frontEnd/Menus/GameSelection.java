package frontEnd.Menus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import backEnd.Data.XMLReadingException;
import backEnd.GameData.GameData;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameSelection {
	private List<Button> buttons;
	private Consumer<GameData> gameDataConsumer;
	

	public GameSelection(Consumer<GameData> gameDataConsumer) {
		this.gameDataConsumer = gameDataConsumer;
		this.create();
	}

	private void create(){
		buttons = new ArrayList<Button>();
		
		Button newButton = new Button();
		newButton.setText("Create A New Game");
		newButton.setOnAction(e -> new GameMaker(new Stage(), gameDataConsumer));
		
		buttons.add(newButton);
		
		Button loadTemplate = new Button();
		loadTemplate.setText("Load a Template Game");
		loadTemplate.setOnAction(e -> showTemplateMenu(new Stage()));
		
		buttons.add(loadTemplate);
		
		Button loadSavedGame = new Button();
		loadSavedGame.setText("Load a Saved Game");
		loadSavedGame.setOnAction(e -> loadGame());
		
		buttons.add(loadSavedGame);
		
	}
	
	public List<Button> getButtons(){
		return this.buttons;
	}
	
	private void loadGame() {
		GameLoader gameLoader = new GameLoader();
		try {
			GameData loadedGameData = gameLoader.loadGame();
			gameDataConsumer.accept(loadedGameData);
		} catch (XMLReadingException e) {
			ErrorDialog errDia = new ErrorDialog();
			errDia.create("Cannot Load Game", e.getMessage());
		}
		
	}

	private void showTemplateMenu(Stage stage) {
		GameLoader gameLoader = new GameLoader();
		
		ButtonMenuImpl templateGames = new ButtonMenuImpl();
   	 	templateGames.setText("Which game?");
   	 	//for(String templateGame : gameLoader.getTemplateTitleList()){
   	 	for(String templateGame : gameLoader.getTemplateTitleListStupid()){
   	 		templateGames.addSimpleButton(templateGame, event -> gameDataConsumer.accept(gameLoader.loadTemplateGame(templateGame)));
   	 	}
   	 	templateGames.create();
		stage.setScene(templateGames.getScene());
		stage.show();
	}

}
