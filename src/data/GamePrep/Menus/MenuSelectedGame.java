package data.GamePrep.Menus;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import data.GamePrep.GameMaker;
import frontEnd.CustomJavafxNodes.ActionButton;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.ButtonScrollPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

public class MenuSelectedGame {
	private static final StringResourceBundle strResources = new StringResourceBundle();
	private ButtonMenuImpl primaryMenu;
	private Stage myStage;
	private Consumer<Object> myConsumerLoadData;
	
	public MenuSelectedGame(ButtonMenuImpl previousMenu, Stage stage, Consumer<Object> consumerLoadData){
		myStage = stage;
		myConsumerLoadData = consumerLoadData;
		File file = new File(strResources.getFromFilePaths("All_Games_Path"));
		String[] directories = file.list( (File current, String name) -> {
			return new File(current,name).isDirectory();
		});
		
		primaryMenu = new ButtonMenuImpl("Select a Game");
				
		ButtonScrollPane buttonScrollPane = new ButtonScrollPane();
		for(String gameName: directories){
			buttonScrollPane.add(new ActionButton(gameName,() -> showGameMenu(primaryMenu, stage, gameName)));
		}
		primaryMenu.addNode(buttonScrollPane.getRoot());
		primaryMenu.addBackButton(previousMenu, stage);
		
	}

	public void display() {
		primaryMenu.display(myStage);
	}
	
	private void showGameMenu(ButtonMenuImpl previousMenu, Stage stage, String game) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Select a Game");
		primaryMenu.addNode(new Label("Game Name: " + game));
		primaryMenu.addSimpleButtonWithHover("Create New Level", () -> {
			GameMaker gameMaker = new GameMaker(stage, myConsumerLoadData, game);
			gameMaker.display();
		}, "Create a new level for this game");
		primaryMenu.addSimpleButtonWithHover("Play Level", () -> chooseLevel(primaryMenu, stage, "templates" , game), "Play from the first level");
		primaryMenu.addSimpleButtonWithHover("Edit Level", () -> chooseLevel(primaryMenu, stage, "templates" , game), "Load a level to edit");
   	 	primaryMenu.addSimpleButtonWithHover("Load Saved Game", () -> chooseLevel(primaryMenu, stage, "saves" , game), "Continue your progress by loading a user-saved game");
   	 	primaryMenu.addBackButton(previousMenu, stage);
   	 	primaryMenu.display(stage);
	}
	
	private void chooseLevel(ButtonMenuImpl previousMenu, Stage stage,String type , String game)
	{
		String folder = "data/games/" + game + "/" + type + "/";
		
		File file = new File(folder);
		file.mkdir();
		
		List<String> directories = Arrays.asList(file.list( (File current, String name) -> {
			return new File(current,name).isDirectory();
		}));
		if(directories.isEmpty()){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No files found");
			alert.setHeaderText("There are no " + type + " files here");
			alert.setContentText("Please select a different option");

			alert.showAndWait();
		} else {
			ButtonMenuImpl levelMenu = new ButtonMenuImpl("Pick a Level!");
			ButtonScrollPane buttonScrollPane = new ButtonScrollPane();
			for(String levelName: directories){
				buttonScrollPane.add(new ActionButton(levelName, () -> {
					myConsumerLoadData.accept(new File(folder + levelName));
					stage.close();
				}));
			}
			levelMenu.addNode(buttonScrollPane.getRoot());
			levelMenu.addBackButton(previousMenu, stage);
			levelMenu.display(stage);
		}
	}


}
