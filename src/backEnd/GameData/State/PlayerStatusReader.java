package backEnd.GameData.State;

import javafx.beans.property.ReadOnlyDoubleProperty;

/**
 * GameStatus interface for front end to access properties to bind to
 * 
 * @author Derek
 *
 */
public interface PlayerStatusReader {
	
	
	/**
	 * @param propertyName
	 * @return ReadOnlyProperty of PlayerStatus item propertyName
	 */
	ReadOnlyDoubleProperty getProperty(String propertyName);
	
	

}
