package backEnd.Mode;

import java.util.Observer;

import javafx.beans.property.SimpleBooleanProperty;

public interface ModeReader {

	public SimpleBooleanProperty getAuthorBooleanProperty();
	
	public void addObserver(Observer o);
	
}
