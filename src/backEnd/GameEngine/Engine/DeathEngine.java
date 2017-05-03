package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import backEnd.Bank.BankControllerImpl;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;

/**
 * Check if objects are dead, remove them from the grid if so + if the object
 * spawns another object when it dies, add it to the grid.
 * 
 * @author Daniel
 *
 */


public class DeathEngine implements Engine {
	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();
	private BankControllerImpl myBank;
	private List<Component> toRemove;
	private Map<Component, Point2D> toAdd;
	private GameData myGameData;
	
	
	public void gameLoop(GameData gameData, double stepTime) {
		toRemove =new ArrayList<Component>();
		myGameData=gameData;
		toAdd= new HashMap<Component, Point2D>();
		myBank=myGameData.getBankController();
		for (Component myComponent : gameData.getState().getComponentGraph().getAllComponents()) {
			if (isDead(myComponent)) {
				toRemove.add(myComponent);
				if(myComponent.<Boolean>getAttribute(STRING_RESOURCES.getFromAttributeNames("IsBoss")).getValue() == true){
					gameData.getStatus().incrementStatusItem(STRING_RESOURCES.getFromStringConstants("BossKillCount"), 1);
				}
				incrementCounters(myComponent);
				if (spawnsOnDeath(myComponent)) {
					Point2D currentLocation = myComponent.<Point2D>getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
					Component newComponent=getNewComponent(myComponent);
					newComponent.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Position"), currentLocation);
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
		return c.<Boolean>getAttribute(STRING_RESOURCES.getFromAttributeNames("SpawnOnDeath")).getValue();
	}
	
	public boolean isDead(Component c){
		return (c.<Integer>getAttribute(STRING_RESOURCES.getFromAttributeNames("Health")).getValue() <=0);
	}
	
	public Component getNewComponent(Component c){
		return myBank.getComponent(c.<String>getAttribute(STRING_RESOURCES.getFromAttributeNames("SpawnOnDeathObject")).getValue());
	}
	
	public void incrementCounters(Component myComponent){
		if (myComponent.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromStringConstants("Enemy"))){
			myGameData.getStatus().incrementStatusItem(STRING_RESOURCES.getFromStringConstants("Money"), myComponent.<Integer>getAttribute(STRING_RESOURCES.getFromAttributeNames("MoneyBounty")).getValue());
			myGameData.getStatus().incrementStatusItem(STRING_RESOURCES.getFromStringConstants("Score"), myComponent.<Integer>getAttribute(STRING_RESOURCES.getFromAttributeNames("ScoreBounty")).getValue());
			myGameData.getStatus().incrementStatusItem(STRING_RESOURCES.getFromStringConstants("KillCount"), 1);
		}
	}

}
