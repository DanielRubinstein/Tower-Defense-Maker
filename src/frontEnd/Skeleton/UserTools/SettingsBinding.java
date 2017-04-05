package frontEnd.Skeleton.UserTools;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SettingsBinding {
	private int myValue;
	private BooleanProperty b;
	public SettingsBinding() {
		myValue = 5;
		b = new SimpleBooleanProperty(myValue%3==0);
	}
	
	
	public BooleanProperty valueProperty(){
		//b = new SimpleBooleanProperty(myValue%3==0);
		return b;
	}
	
	public void setValProp(BooleanProperty m){
		b=m;
	}
	
	public boolean isValid(){
		return myValue%3==0;
	}
	
	public void addOne(){
		myValue++;
		if(myValue%3==0){
			b.setValue(true);
		}else{
			b.setValue(false);
		}
	}
	public int getValue(){
		return myValue;
	}

}
