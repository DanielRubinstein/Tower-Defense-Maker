package frontEnd.Skeleton.UserTools;

import java.util.List;

import backEnd.LevelProgression.LevelProgressionControllerEditor;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.DragDrop.ListDragDrop;
import frontEnd.CustomJavafxNodes.DragDrop.ListDragDropImpl;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;

/**
 * This class allows the user to view and edit levels for a game.
 * @author Tim
 *
 */
public class LevelView implements PopUp{
	private StringResourceBundle strResources = new StringResourceBundle();
	private NumericResourceBundle numResources = new NumericResourceBundle();
	private GridPane myRoot;
	private View myView;
	private LevelProgressionControllerEditor myLevelContr;
	
	public LevelView(View view, LevelProgressionControllerEditor levels){
		myRoot = new GridPane();
		myView = view;
		myLevelContr = levels;
		initializeRoot();
		createStructureBoxes();	
	}
	
	private void initializeRoot(){
		myRoot.setPadding(new Insets(numResources.getFromSizing("StandardSpacing")));
	}
	
	@Override
	public void displayOnStage(Stage stage) {
		Stage myStage = new Stage();
		Scene myScene = new Scene(myRoot);
		myScene.getStylesheets().add(strResources.getFromStringConstants("DEFAULT_CSS"));
		myStage.setScene(myScene);
		myStage.setOnCloseRequest(e -> myLevelContr.saveGamesMap());
		myStage.show();
	}
	
	private void createStructureBoxes(){
		Label title = new Label("Game Structure");
		title.setUnderline(true);
		myRoot.add(title, 0, 0);

		Label game = new Label(strResources.getFromStringConstants("GameTitle"));
		myRoot.add(game, 0, 1);

		VBox gameOutline = createSingleBox(0);
		VBox levelOutline = null;

		populateGame(gameOutline,myLevelContr.getGameList(),levelOutline);
		Label levels = new Label(strResources.getFromStringConstants("LevelTitle"));
		myRoot.add(levels, 1, 1);
	}
	
	private void populateGame(VBox wrapper,List<String> toAdd,VBox addToBox){
		for (String str : toAdd){
			Button strButton = new Button(str);
			strButton.setOnAction(e -> populateLevels(str));
			wrapper.getChildren().add(strButton);
		}
	}
	
	private void populateLevels(String gameName){
		List<String> gameLevels = myLevelContr.getLevelList(gameName);
		
		ListDragDrop<String> levelDrop = new ListDragDropImpl<String>(FXCollections.observableArrayList(gameLevels));
		levelDrop.disabledProperty().bind(myView.getBooleanAuthorModeProperty().not());
		levelDrop.changedListProperty().addListener((o, oldV, newV) -> {
			if(newV){
				List<String> orderedLevels = levelDrop.getList();
				myLevelContr.setLevelList(gameName, orderedLevels);
				levelDrop.acceptChange();
			}
		});
		myRoot.add(levelDrop.getRoot(), 1, 2);
	}
	
	private VBox createSingleBox(int col){
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		VBox wrapper = new VBox();
		wrapper.setSpacing(numResources.getFromSizing("StandardSpacing"));
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		myRoot.getColumnConstraints().add(column1);
		scroll.setPrefHeight(numResources.getScreenConstants().getScreenGridHeight());
		wrapper.maxWidthProperty().bind(myRoot.widthProperty().divide(3));
		scroll.setContent(wrapper);
		myRoot.add(scroll, col, 2);
		return wrapper;
	}


}
