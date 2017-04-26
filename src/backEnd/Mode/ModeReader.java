package backEnd.Mode;

import backEnd.GameData.State.SerializableObserver;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public interface ModeReader {

	public SimpleBooleanProperty getAuthorBooleanProperty();
	
	public void addObserver(SerializableObserver o);

	SimpleStringProperty getGameStringProperty();

	SimpleStringProperty getLevelStringProperty();
	
}
