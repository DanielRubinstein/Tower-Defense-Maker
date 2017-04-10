package backEnd.GameData;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserAttributeImpl implements UserAttribute {
	private final SimpleStringProperty name;
	private final SimpleDoubleProperty value;

	public UserAttributeImpl(String key, Double value) {
		this.name = new SimpleStringProperty(key);
		this.value = new SimpleDoubleProperty(value);
	}
	
	@Override
	public Double getValue() {
		return value.get();
	}
	
	@Override
	public String getName(){
		return name.get();
	}
	
	@Override
	public void setName(String newName){
		this.name.set(newName);
	}
	
	@Override
	public void setValue(Double newValue){
		this.value.set(newValue);
	}

}
