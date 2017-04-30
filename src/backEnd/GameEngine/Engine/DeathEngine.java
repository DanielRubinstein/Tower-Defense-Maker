package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.Bank.BankControllerImpl;
import backEnd.GameData.GameData;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.ComponentGraphImpl;
import javafx.geometry.Point2D;

/**
 * Check if objects are dead, remove them from the grid if so + if the object
 * spawns another object when it dies, add it to the grid.
 * 
 * @author Daniel
 *
 */

public class DeathEngine implements Engine {
	private final String ATTRIBUTE_BUNDLE_NAME = "resources.allAttributeNames";
	private final ResourceBundle ATTRIBUTE_RESOURCE_BUNDLE = ResourceBundle.getBundle(ATTRIBUTE_BUNDLE_NAME);
	private BankControllerImpl myBank;
	public void gameLoop(GameData gameData, double stepTime) {
		List<Component> toRemove=new ArrayList<Component>();
		Map<Component, Point2D> toAdd=new HashMap<Component, Point2D>();
		myBank=gameData.getBankController();
		for (Component myComponent : gameData.getState().getComponentGraph().getAllComponents()) {
			if (isDead(myComponent)) {
				toRemove.add(myComponent);
				gameData.getStatus().incrementStatusItem("KillCount", 1);
				gameData.getStatus().incrementStatusItem("Money", myComponent.<Integer>getAttribute("MoneyBounty").getValue());
				gameData.getStatus().incrementStatusItem("Score", myComponent.<Integer>getAttribute("ScoreBounty").getValue());
				if (spawnsOnDeath(myComponent)) {
					Point2D currentLocation = myComponent.<Point2D>getAttribute(ATTRIBUTE_RESOURCE_BUNDLE.getString("Position")).getValue();
					Component newComponent=getNewComponent(myComponent);
					newComponent.setAttributeValue(ATTRIBUTE_RESOURCE_BUNDLE.getString("Position"), currentLocation);
					toAdd.put(getNewComponent(myComponent), currentLocation);
				}
			}

		}
		for (Component toDelete: toRemove){
			gameData.getState().getComponentGraph().removeComponent(toDelete);
		}
		for (Component c: toAdd.keySet()){
			gameData.getState().getComponentGraph().addComponentToGrid(c, toAdd.get(c));
		}
	}
	

	public boolean spawnsOnDeath(Component c){
		return c.<Boolean>getAttribute(ATTRIBUTE_RESOURCE_BUNDLE.getString("SpawnOnDeath")).getValue();
	}
	
	public boolean isDead(Component c){
		return (c.<Integer>getAttribute(ATTRIBUTE_RESOURCE_BUNDLE.getString("Health")).getValue() <=0);
	}
	
	public Component getNewComponent(Component c){
		return myBank.getComponent(c.<String>getAttribute(ATTRIBUTE_RESOURCE_BUNDLE.getString("SpawnOnDeathObject")).getValue());
	}

}
