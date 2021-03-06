package data.GamePrep;

import java.io.File;
import java.util.function.Consumer;

import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.NumberChanger;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;

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
	private static final NumericResourceBundle numResources = new NumericResourceBundle();
	
	public GameMaker(Stage stage, Consumer<Object> gameDataConsumer, String name) {
		gameName = name;
		allSelections = new ButtonMenuImpl(strResources.getFromStringConstants("StartingInput"));
		allSelections.addNode(new Label(strResources.getFromStringConstants("GameName") + name));
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
		Integer min = numResources.getFromSizing("MinTileInDimension").intValue();
		Integer def = numResources.getFromSizing("DefaultTileInDimension").intValue();
		Integer max = numResources.getFromSizing("MaxTileInDimension").intValue();
		
		myTilesWide = setInputSliderFields(strResources.getFromStringConstants("TilesWide"),min,def,max);
		myTilesHigh = setInputSliderFields(strResources.getFromStringConstants("TilesHigh"),min,def,max);
		setSubmit();
	}
	private void setSubmit() {
		allSelections.addSimpleButtonWithHover(strResources.getFromStringConstants("Submit"), () ->  {
			StartingInput allValues = createStartingInput();
			makeDirectory();
			onSubmit.accept(allValues);
			myStage.close();
		}, strResources.getFromStringConstants("SubmitHover"));
	}

	private void makeDirectory() {
		String templateFormat = strResources.getFromFilePaths("Template_Path_Format");
		String savesFormat = strResources.getFromFilePaths("Save_Path_Format");
		File newTemplatesDirectory = new File(String.format(templateFormat, gameName));
		File newSavesDirectory = new File(String.format(savesFormat, gameName));
		newTemplatesDirectory.mkdirs();
		newSavesDirectory.mkdirs();
	}

	private StartingInput createStartingInput() {
		StartingInput allValues = new StartingInput();
		allValues.setTilesWide(myTilesWide.getValue().intValue());
		allValues.setTilesHigh(myTilesHigh.getValue().intValue());
		allValues.setGameName(gameName);
		return allValues;
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
