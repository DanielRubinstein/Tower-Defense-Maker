package frontEnd.Skeleton.UserTools;

import backEnd.LevelProgression.LevelProgressionControllerEditor;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelEditor {

	private HBox gameEditor;
	private HBox levelEditor;
	private HBox allLevelsEditor;
	private String currentGame;
	private VBox levelsView;
	private LevelProgressionControllerEditor myContr;
	
	public LevelEditor(double width, VBox levels, LevelProgressionControllerEditor myLevels){
		levelsView = levels;
		myContr = myLevels;
		
		createAllLevelsEditor();
		createGameEditor();
		createLevelEditor();
	}
	
	public void setGameName(String name){
		currentGame = name;
	}
	private void createGameEditor(){
		gameEditor = new HBox();
		Label curGame = new Label();
		curGame.textProperty().bind(new SimpleStringProperty(currentGame));
		Button removeGame = new Button("Remove Game");
		TextField addText = new TextField();
		Button addGame = new Button("Add Game");
		addGame.setOnAction(e -> {
			myContr.addNewGame(addText.getText());
			
		});
		HBox removeBox = new HBox();
		removeBox.getChildren().addAll(removeGame,curGame);
		HBox addBox = new HBox();
		addBox.getChildren().addAll(addGame,addText);
		
	}
	private void createLevelEditor(){
		levelEditor = new HBox();

	}
	private void remove(Button selectedLevel) {
		levelsView.getChildren().remove(selectedLevel);
		myContr.removeLevelFromGame(currentGame, selectedLevel.getText());
	}
	public void populateLevelEditor(Button parent){
		levelEditor.getChildren().clear();
		Button remove  = new Button("remove");
		remove.setOnAction(e -> remove(parent));
		Button up = new Button("up");
		up.setOnAction(e -> incrementIndex(parent,-1));
		Button down = new Button("down");
		down.setOnAction(e -> incrementIndex(parent,1));
		levelEditor.getChildren().addAll(remove,up,down);
	}

	private void createAllLevelsEditor(){
		allLevelsEditor = new HBox();
		Button instructions = new Button("Add this to levels:");
		Label levelToAdd = new Label();
		instructions.setOnAction(e -> addNewLevel(levelToAdd.getText()));
		allLevelsEditor.getChildren().addAll(instructions,levelToAdd);
	}
	public void makeLevelToAdd(String level){
		Label l = (Label) allLevelsEditor.getChildren().get(1);
		l.setText(level);
	}
	private void addNewLevel( String s){
		Button b = new Button(s);
		b.setOnAction(e -> populateLevelEditor(b));
		addAtIndex(b,levelsView.getChildren().size());
		myContr.addLevelToGame(currentGame, s);
	}
	public void populateLevelToAdd(String text){
		Label l = (Label) allLevelsEditor.getChildren().get(1);
		l.setText(text);
	}
	
	private void addAtIndex(Button b, int newIndex){
		levelsView.getChildren().remove(b);
		levelsView.getChildren().add(newIndex, b);
	}
	private void incrementIndex(Button b, int increment){
		int oldIndex = levelsView.getChildren().indexOf(b);
		int newIndex = oldIndex + increment;
		if(newIndex <0 || newIndex > levelsView.getChildren().size()-1){	
			return;
		}
		levelsView.getChildren().remove(b);
		levelsView.getChildren().add(oldIndex + increment, b);
	}
	
	
	public Node getGameEditor(){
		return gameEditor;
	}
	
	public Node getLevelEditor(){
		return levelEditor;
	}

	public Node getAllLevelsEditor(){
		return allLevelsEditor;
	}
}
