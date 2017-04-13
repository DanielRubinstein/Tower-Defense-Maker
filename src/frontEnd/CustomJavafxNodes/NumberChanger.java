package frontEnd.CustomJavafxNodes;

import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Slider;

public class NumberChanger implements SkeletonObject{
	Slider myRoot;
	
	public NumberChanger(Double min, Double max, Double start, Double increment){
		myRoot = createSlider(min, max, start, increment);
	}
	public NumberChanger(Integer min, Integer max, Integer start, Integer increment){
		myRoot = createSlider(min.doubleValue(), max.doubleValue(), start.doubleValue(), increment.doubleValue());
	}
	
	
	private Slider createSlider(Double min, Double max, Double start, Double increment){
		Slider slider = new Slider();
		slider.setMin(min);
		slider.setMax(max);
		slider.setValue(start);
		slider.setBlockIncrement(increment);
		return slider;
		
	}
	
	public void addListener(ChangeListener<? super Number> cL){
		myRoot.valueProperty().addListener(cL);
	}
	
	public Double getValue(){
		return myRoot.getValue();
	}


	@Override
	public Node getRoot() {
		return myRoot;
	}
}
