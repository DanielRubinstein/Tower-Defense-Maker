package frontEnd.CustomJavafxNodes;

import java.util.function.Function;

import frontEnd.Skeleton.SkeletonObject;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import resources.constants.StringResourceBundle;

public class NumberChanger implements SkeletonObject{
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private Slider myRoot;
	
	/**
	 * Initializes the numberChanger with the given parameters
	 * @param min
	 * @param max
	 * @param start
	 * @param increment
	 */
	public NumberChanger(Number min, Number max, Number start, Number increment){
		myRoot = createSlider(min.doubleValue(), max.doubleValue(), start.doubleValue(), increment.doubleValue());
	}
	
	
	private Slider createSlider(Double min, Double max, Double start, Double increment){
		Slider slider = new Slider();
		slider.setMin(min);
		slider.setMax(max);
		slider.setValue(start);
		slider.setBlockIncrement(increment);
		//slider.setShowTickLabels(true);
		return slider;
	}
	
	public void addListener(ChangeListener<? super Number> cL){
		myRoot.valueProperty().addListener(cL);
	}
	
	public Double getValue(){
		return myRoot.getValue();
	}
	
	private HBox create(String labelFormat, Function<Number, ? extends Number> toProperNumber){
		HBox h = new HBox();
		h.getChildren().add(this.getRoot());
		Label currentVal = new Label(String.format(labelFormat, toProperNumber.apply(this.getValue())));
		this.addListener( (SerializableObservable, oldValue, newValue)->{
			myRoot.setValue(toProperNumber.apply(newValue).doubleValue());
			currentVal.setText(String.format(labelFormat, toProperNumber.apply(newValue)));
		});
		h.getChildren().add(currentVal);
		return h;
	}
	
	
	public HBox addIntegerIndicator(){
		Function<Number, Integer> toInteger = (num) -> num.intValue();
		return create(stringResourceBundle.getFromStringConstants("SingleIntegerWithParenthesis"), toInteger);
	}
	
	public HBox addDoubleIndicator(){
		Function<Number, Double> toDouble = (num) -> num.doubleValue();
		return create(stringResourceBundle.getFromStringConstants("SingleDoubleRoundedToTenthsWithParenthesis"), toDouble);
	}


	@Override
	public Node getRoot() {
		return myRoot;
	}
}
