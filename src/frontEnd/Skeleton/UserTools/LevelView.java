package frontEnd.Skeleton.UserTools;

import java.util.List;

import backEnd.LevelProgression.LevelProgressionControllerEditor;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.constants.StringConstants;


public class LevelView {
	private StringConstants stringConstants = new StringConstants();
	private GridPane myRoot;
	private Stage myStage;
	private LevelProgressionControllerEditor myLevels;
	private Scene myScene;
	private Node gameEditor;
	private Node levelEditor;
	private Node allLevelsEditor;
	private LevelEditor myLevelEditor;
	
	public LevelView(LevelProgressionControllerEditor levels, Stage parentStage)
	{
		myRoot = new GridPane();
		myLevels = levels;
		myStage = new Stage();
		myStage.initOwner(parentStage);
		myStage.initModality(Modality.APPLICATION_MODAL);
		myStage.setOnCloseRequest(e -> levels.saveGamesMap());
		//myRoot.prefWidthProperty().bind(readOnlyDoubleProperty);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setVgap(20);
		myRoot.setHgap(20);
		createStructureBoxes();

		
	}
	
	public void launch(){
		myScene = new Scene(myRoot);
		myScene.getStylesheets().add(stringConstants.getDefaultCSS());
		myStage.setScene(myScene);
		myStage.show();
	}
	
	public void createStructureBoxes(){
		Label title = new Label("Game Structure");
		myRoot.add(title, 1, 0);

		Label game = new Label("Game");
		myRoot.add(game, 0, 1);
		VBox gameOutline = createSingleBox(0);
		VBox levelOutline = createSingleBox(1);
		VBox allLevelsOutline = createSingleBox(2);
		
		populateGame(gameOutline,myLevels.getGameList(),levelOutline);
		populateAllLevels(allLevelsOutline,myLevels.getFullLevelList());
		
		Label levels = new Label("Levels");
		myRoot.add(levels, 1, 1);
		Label allLevels = new Label("All Levels");
		myRoot.add(allLevels, 2, 1);
		myLevelEditor = new LevelEditor(100,levelOutline,gameOutline,myLevels);
		createBottomEditor();
	}
	private void populateGame(VBox wrapper,List<String> toAdd,VBox addToBox){
		for (String str : toAdd){
			Button strButton = new Button(str);
			strButton.setOnAction(e -> populateLevels(str,addToBox));
			wrapper.getChildren().add(strButton);
		}
	}
	private void populateLevels(String gameName,VBox wrapper){
		wrapper.getChildren().clear();
		List<String> levels = myLevels.getLevelList(gameName);
		for (String str : levels){
			Button strButton = new Button(str);
			strButton.setOnAction(e -> myLevelEditor.populateLevelEditor(strButton));
			wrapper.getChildren().add(strButton);
		}
		myLevelEditor.setGameName(gameName);
	}
	private void populateAllLevels(VBox wrapper,List<String> toAdd){
		for (String str : toAdd){
			Button strButton = new Button(str);
			strButton.setOnAction(e -> myLevelEditor.populateLevelToAdd(str));
			wrapper.getChildren().add(strButton);
		}
	}
	public VBox createSingleBox(int col){
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setPrefHeight(300);
		VBox wrapper = new VBox();
		wrapper.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		myRoot.getColumnConstraints().add(column1);
		wrapper.maxWidthProperty().bind(myRoot.widthProperty().divide(3));
		wrapper.setPrefHeight(300);
		scroll.setContent(wrapper);
		myRoot.add(scroll, col, 2);
		return wrapper;
	}

	
	private void createBottomEditor(){
		gameEditor = myLevelEditor.getGameEditor();
		myRoot.add(gameEditor, 0, 3);
		
		levelEditor = myLevelEditor.getLevelEditor();
		myRoot.add(levelEditor, 1, 3);
		
		allLevelsEditor = myLevelEditor.getAllLevelsEditor();
		myRoot.add(allLevelsEditor, 2, 3);
	}

}
