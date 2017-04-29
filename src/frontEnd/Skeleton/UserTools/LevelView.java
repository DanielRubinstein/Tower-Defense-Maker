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
import resources.constants.StringResourceBundle;


public class LevelView {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private GridPane myRoot;
	private Stage myStage;
	private LevelProgressionControllerEditor myLevelContr;
	private Scene myScene;
	private Node gameEditor;
	private Node levelEditor;
	private LevelEditor myLevelEditor;
	
	public LevelView(LevelProgressionControllerEditor levels, Stage parentStage){
		myRoot = new GridPane();
		myLevelContr = levels;
		initializeRoot(parentStage);
		createStructureBoxes();	
	}
	private void initializeRoot(Stage parentStage){
		myStage = new Stage();
		myStage.initOwner(parentStage);
		myStage.initModality(Modality.APPLICATION_MODAL);
		myStage.setOnCloseRequest(e -> myLevelContr.saveGamesMap());
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setVgap(20);
		myRoot.setHgap(20);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setVgap(20);
		myRoot.setHgap(20);
	}
	
	public void launch(){
		
		myScene = new Scene(myRoot);
		myScene.getStylesheets().add(stringResourceBundle.getFromStringConstants("DEFAULT_CSS"));
		myStage.setScene(myScene);
		myStage.show();
	}
	
	public void createStructureBoxes(){
		Label title = new Label("Game Structure");
		title.setUnderline(true);
	
		myRoot.add(title, 0, 0);

		Label game = new Label("Game (click on game to view levels)");
		myRoot.add(game, 0, 1);
		VBox gameOutline = createSingleBox(0);
		VBox levelOutline = createSingleBox(1);
		
		populateGame(gameOutline,myLevelContr.getGameList(),levelOutline);
		Label levels = new Label("Levels");
		myRoot.add(levels, 1, 1);
		
		myLevelEditor = new LevelEditor(100,levelOutline,gameOutline,myLevelContr);
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
		List<String> levels = myLevelContr.getLevelList(gameName);
		for (String str : levels){
			Button strButton = new Button(str);
			strButton.setOnAction(e -> myLevelEditor.populateLevelEditor(strButton));
			wrapper.getChildren().add(strButton);
		}
		myLevelEditor.setGameName(gameName);
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
		
	}

}
