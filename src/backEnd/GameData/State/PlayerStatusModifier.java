package backEnd.GameData.State;

import java.util.Collection;

/**
 * Modifier interface for back end
 * 
 * @author Derek
 *
 */
public interface PlayerStatusModifier extends PlayerStatusReader{
	
	/**
	 * adds valToAdd to current val of player status item itemName
	 * 
	 * @param itemName
	 * @param valToAdd
	 */
	void incrementStatusItem(String itemName, double valToAdd);
	

	/**
	 * subtracts val from itemName player status item
	 * 
	 * @param itemName
	 * @param valToSubtract
	 * @return true if new value is less than or equal to 0
	 */
	//FIXME should this method set value to 0 if it goes below 0 so that front end does not display a negative number
	boolean decrementStatusItem(String itemName, double valToSubtract);
	
	/**
	 * 
	 * @param itemName
	 * @return value of player status item itemName
	 */
	double getStatusItemValue(String itemName);
	
	Collection<String> getKeySet();
	
	void setStatusItemValue(String itemKey, double newVal);
	

}
