package backEnd.GameData.GameStatus;

import javafx.beans.property.ReadOnlyDoubleProperty;

/**
 * GameStatus interface for front end to access properties to bind to
 * 
 * @author Derek
 *
 */
public interface GameStatusReader {
	
	
	/**
	 * @param propertyName
	 * @return ReadOnlyProperty of GameStatus item propertyName
	 */
	ReadOnlyDoubleProperty getProperty(String propertyName);
	
	

}
