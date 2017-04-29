package data.GamePrep;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.NumberChanger;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

/**
 * If the user decides to create a game, this class is instantiated and offers a
 * series of dialog boxes to the user in order to create a game
 * 
 * @author Miguel Anderson, Tim Overeem
 *
 */
public class GameMaker {
	
	private ButtonMenuImpl allSelections;
	private NumberChanger myTilesWide;
	private NumberChanger myTilesHigh;
	private Consumer<Object> onSubmit;
	private Stage myStage;
	private static final StringResourceBundle strResources = new StringResourceBundle();
	
	public GameMaker(Stage stage, Consumer<Object> gameDataConsumer) {
		allSelections = new ButtonMenuImpl("Starting Values!");
		onSubmit = gameDataConsumer;
		setInputFields();
		myStage = stage;
		allSelections.display(myStage);
	}

	private void setInputFields() {
		myTilesWide = setInputSliderFields("Number Tiles Wide",1,10,40);
		myTilesHigh = setInputSliderFields("Number Tiles High",1,10,40);
		
		newGameOrLevel();

	}
	private void newGameOrLevel() {
		GridPane bothOptions = new GridPane();
		

		makeNewGame(bothOptions);
		
		Label or = new Label("OR      ");
		bothOptions.add(or, 1, 0);
		
		bothOptions.add(makeGameSelection(), 2, 1);
		
		allSelections.addNode(bothOptions);
	}
	private void makeNewGame(GridPane grid){
		TextField gameName= new TextField();
		Button submitGame = new Button("Create New Game");
		submitGame.setOnAction(e -> submitStartingInput(gameName.getText()));	
		Label title = new Label("Make new game");
		grid.add(gameName, 0, 1);
		grid.add(submitGame, 0, 2);
		grid.add(title, 0, 0);
	}
	private Node makeGameSelection(){
		ScrollPane scroll = new ScrollPane();
		scroll.setMaxHeight(150);
		scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		VBox wrapper = new VBox();
		Label title = new Label("Add to already-made game");
		wrapper.getChildren().add(title);
		for(String game: getFullLevelList()){
			Button gameButton = new Button(game);
			gameButton.setOnAction(e -> submitStartingInput(game));
			wrapper.getChildren().add(gameButton);
		}
		
		scroll.setContent(wrapper);
		
		return scroll;
	}
	private void submitStartingInput(String gameName){
		StartingInput allValues = new StartingInput();
		allValues.setTilesWide(myTilesWide.getValue().intValue());
		allValues.setTilesHigh(myTilesHigh.getValue().intValue());
		allValues.setGameName(gameName);
		onSubmit.accept(allValues);
		myStage.close();
	}
	
	private List<String> getFullLevelList(){
		File file = new File(strResources.getFromFilePaths("All_Games_Path"));
		String[] directories = file.list( (File current, String name) -> {
			return new File(current,name).isDirectory();
		});
		
		return Arrays.asList(directories);
	}

	private NumberChanger setInputSliderFields(String text, Integer min, Integer start, Integer max){
		HBox tileWidth = new HBox();
		Label name = new Label(text);
		NumberChanger slide = new NumberChanger(min, max, start, 1);
		tileWidth.getChildren().addAll(name, slide.addIntegerIndicator());
		allSelections.addNode(tileWidth);
		return slide;
	}


}
