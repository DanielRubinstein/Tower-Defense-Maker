package backEnd.GameData.GameStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;

/**
 *
 * @author Derek
 *
 */
public class GameStatus implements GameStatusReader, GameStatusModifier {
	
	private final static String GAME_STATUS_ITEMS_PATH = "resources/gameStatusItems";
	private final static ResourceBundle myGameStatusItemsResources = ResourceBundle.getBundle(GAME_STATUS_ITEMS_PATH);
	private Map<String, ReadOnlyDoubleWrapper> myStatusItems = new HashMap<String, ReadOnlyDoubleWrapper>();

	public GameStatus() {
		for(String item : myGameStatusItemsResources.keySet()){
			double defaultVal = Double.parseDouble(myGameStatusItemsResources.getString(item));
			myStatusItems.put(item, new ReadOnlyDoubleWrapper(defaultVal));
		}
	}

	@Override
	public ReadOnlyDoubleProperty getProperty(String propertyName) {
		return myStatusItems.get(propertyName).getReadOnlyProperty();
	}

	@Override
	public void incrementStatusItem(String itemName, double valToAdd) {
		double oldVal = myStatusItems.get(itemName).getValue();
		myStatusItems.get(itemName).set(oldVal + valToAdd);
	}

	@Override
	public boolean decrementStatusItem(String itemName, double valToSubtract) {
		double oldVal = myStatusItems.get(itemName).getValue();
		myStatusItems.get(itemName).set(oldVal - valToSubtract);
		return myStatusItems.get(itemName).getValue() <= 0;
	}

	@Override
	public double getStatusItemValue(String itemName) {
		return myStatusItems.get(itemName).getValue();
	}

}
