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
	private String gameName;
	private static final StringResourceBundle strResources = new StringResourceBundle();
	
	public GameMaker(Stage stage, Consumer<Object> gameDataConsumer, String name) {
		gameName = name;
		allSelections = new ButtonMenuImpl("Pick the starting values!");
		allSelections.addNode(new Label("Game Name: " + name));
		onSubmit = gameDataConsumer;
		setInputFields();
		myStage = stage;
	}
	
	public GameMaker(Stage stage, Consumer<Object> gameDataConsumer, String name, ButtonMenuImpl previousMenu){
		this(stage, gameDataConsumer, name);
		allSelections.addBackButton(previousMenu, stage);
	}
	
	public void display(){
		allSelections.display(myStage);
	}

	private void setInputFields() {
		myTilesWide = setInputSliderFields("Number Tiles Wide",1,10,40);
		myTilesHigh = setInputSliderFields("Number Tiles High",1,10,40);
		setSubmit();
	}
	private void setSubmit() {
		allSelections.addPrimarySimpleButtonWithHover("Submit", () ->  {
			StartingInput allValues = new StartingInput();
			allValues.setTilesWide(myTilesWide.getValue().intValue());
			allValues.setTilesHigh(myTilesHigh.getValue().intValue());
			allValues.setGameName(gameName);
			onSubmit.accept(allValues);
			myStage.close();
		}, "Submit these values to continue");
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
