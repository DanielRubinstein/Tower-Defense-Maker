package frontEnd.CustomJavafxNodes;

import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class NumberChanger implements SkeletonObject{
	Slider myRoot;
	
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
	
	public HBox addIntegerIndicator(){
		HBox h = new HBox();
		h.getChildren().add(this.getRoot());
		Label currentVal = new Label(String.format("(%d)", this.getValue().intValue()));
		this.addListener( (observable, oldValue, newValue)->{
			myRoot.setValue(newValue.intValue());
			currentVal.setText(String.format("(%d)", newValue.intValue()));
		});
		h.getChildren().add(currentVal);
		return h;
	}
	
	public HBox addDoubleIndicator(){
		HBox h = new HBox();
		h.getChildren().add(this.getRoot());
		Label currentVal = new Label(String.format("(%1$.1f)", this.getValue().doubleValue()));
		this.addListener( (observable, oldValue, newValue)->{
			currentVal.setText(String.format("(%1$.1f)", newValue.doubleValue()));
		});
		h.getChildren().add(currentVal);
		return h;
	}


	@Override
	public Node getRoot() {
		return myRoot;
	}
}
