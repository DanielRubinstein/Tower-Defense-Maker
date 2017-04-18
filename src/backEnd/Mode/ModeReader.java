package backEnd.Mode;

import javafx.beans.property.SimpleBooleanProperty;

public interface ModeReader {
	
	public String getGameModeString();

	public SimpleBooleanProperty getAuthorBooleanProperty();
	
}
