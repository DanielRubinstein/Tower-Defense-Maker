package backEnd.Mode;

import java.util.Observer;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public interface ModeReader {

	public SimpleBooleanProperty getAuthorBooleanProperty();
	
	public void addObserver(Observer o);

	SimpleStringProperty getGameStringProperty();

	SimpleStringProperty getLevelStringProperty();
	
}
