package data.GamePrep;

import java.util.function.Consumer;

import frontEnd.CustomJavafxNodes.NumberChanger;
import frontEnd.Menus.ButtonMenuImpl;
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
		myTilesWide = setInputSliderFields("Number Tiles Wide",1d,10d,40d);
		myTilesHigh = setInputSliderFields("Number Tiles High",1d,10d,40d);
		setSubmit();
	}
	private void setSubmit() {
		allSelections.addSimpleButton("Submit", e ->  {
			StartingInput allValues = new StartingInput();
			allValues.setTilesWide(myTilesWide.getValue().intValue());
			allValues.setTilesHigh(myTilesHigh.getValue().intValue());
			onSubmit.accept(allValues);
			myStage.close();
		});
	}

	private NumberChanger setInputSliderFields(String text, Double min, Double start, Double max){
		HBox tileWidth = new HBox();
		Label name = new Label(text);
		NumberChanger slide = new NumberChanger(min, max, start, 1d);
		Label currentVal = new Label(String.format("Current value: %f", start));
		slide.addListener( (observable, oldValue, newValue)->currentVal.setText(String.format("Current value: %d", newValue.intValue())));
		tileWidth.getChildren().addAll(name,slide.getRoot(),currentVal);
		allSelections.addNode(tileWidth);
		return slide;
	}


}
