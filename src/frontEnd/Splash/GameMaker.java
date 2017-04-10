package frontEnd.Splash;

import java.util.function.Consumer;

import frontEnd.Menus.ButtonMenu;
import frontEnd.Menus.ButtonMenuImpl;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	private Slider myTilesWide;
	private Slider myTilesHigh;
	private Consumer<StartingInput> onSubmit;
	private Stage myStage;
	
	public GameMaker(Stage stage, Consumer<StartingInput> gameDataConsumer) {
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
		allSelections.addSimpleButton("Submit", e ->  {
			StartingInput allValues = new StartingInput();
			allValues.setTilesWide((int)myTilesWide.getValue());
			allValues.setTilesHigh((int)myTilesHigh.getValue());
			onSubmit.accept(allValues);
			myStage.close();
		});
	}

	private Slider setInputSliderFields(String text, int min, int start, int max){
		HBox tileWidth = new HBox();
		Label name = new Label(text);
		Slider slide = createSlider(1,10,40);
		Label currentVal = new Label(String.format("Current value: %d", start));
		slide.valueProperty().addListener( (observable, oldValue, newValue)->currentVal.setText(String.format("Current value: %d", newValue.intValue())));
		tileWidth.getChildren().addAll(name,slide,currentVal);
		allSelections.addNode(tileWidth);
		return slide;
	}
	
	
	private Slider createSlider(int min, int start, int max){
		Slider slider = new Slider();
		slider.setMin(min);
		slider.setMax(max);
		slider.setValue(start);
		slider.setBlockIncrement(1);
		return slider;
		
	}


}
