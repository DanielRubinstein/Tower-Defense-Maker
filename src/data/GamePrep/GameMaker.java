package data.GamePrep;

import java.util.function.Consumer;

import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.NumberChanger;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * If the user decides to create a game, this class is instantiated and offers a
 * series of dialog boxes to the user in order to create a game
 * 
 * @author Miguel Anderson
 *
 */
public class GameMaker {
	
	private ButtonMenuImpl allSelections;
	private NumberChanger myTilesWide;
	private NumberChanger myTilesHigh;
	private Consumer<Object> onSubmit;
	private Stage myStage;
	
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
		setSubmit();
	}
	private void setSubmit() {
		allSelections.addPrimarySimpleButtonWithHover("Submit", () ->  {
			StartingInput allValues = new StartingInput();
			allValues.setTilesWide(myTilesWide.getValue().intValue());
			allValues.setTilesHigh(myTilesHigh.getValue().intValue());
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