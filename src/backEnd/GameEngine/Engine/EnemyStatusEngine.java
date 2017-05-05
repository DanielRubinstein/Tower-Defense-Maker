package backEnd.GameEngine.Engine;

import java.util.Collection;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import resources.constants.StringResourceBundle;

/**
 * Updates enemy status effects upon component collision
 * currently supported: slow/haste, poison
 * @author Alex
 *
 */
public class EnemyStatusEngine implements Engine {

	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		Collection<Component> components = gameData.getState().getComponentGraph().getAllComponents();
		for (Component component : components) {
			if (component.<String>getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromValueNames("EnemyType"))) {
				updatePoison(stepTime, component);
				updateSlowed(stepTime, component);
			}
		}
	}

	/**
	 * Upon collision between attacker and target, poisons the target
	 * if the attacker has the poison attribute
	 * Removes poison status if the total amount of PoisonTime has elapsed
	 * @param stepTime
	 * @param component the target of the action (usually an Enemy)
	 */
	private void updatePoison(double stepTime, Component component) {
		double poisonTime = component.<Double>getAttribute(STRING_RESOURCES.getFromAttributeNames("PoisonTime")).getValue();
		double poisonFactor = component.<Double>getAttribute(STRING_RESOURCES.getFromAttributeNames("PoisonFactor")).getValue();
		int health = component.<Integer>getAttribute(STRING_RESOURCES.getFromAttributeNames("Health")).getValue();
		
		if (poisonTime > 0) {
			if (poisonTime - stepTime < 0) { // If poison should only do partial
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Health"), health - poisonFactor * poisonTime);
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("PoisonTime"), new Double(0));
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("PoisonFactor"), new Double(0));
			} else { // Full poison tick
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Health"), health - poisonFactor * stepTime);
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("PoisonTime"), poisonTime - stepTime);
			}
		}
	}

	/**
	 * Upon collision between attacker and target, slows the target
	 * if the attacker has the Slow attribute
	 * Removes slow status if the total amount of SlowTime has elapsed
	 * @param stepTime
	 * @param component the target of the action (usually an Enemy)
	 */
	private void updateSlowed(double stepTime, Component component) {
		double slowTime = component.<Double>getAttribute("SlowTime").getValue();
		double normalSpeed = component.<Double>getAttribute("Speed").getValue();
		//System.out.println(" slow time " +slowTime);
		if (slowTime > 0) {
			//System.out.println(" **** updating slowed");
			if (slowTime - stepTime < 0) { // If slow should only do partial
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("SlowTime"), new Double(0));
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("SlowFactor"), new Double(0));
				//component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("CurrentSpeed"), new Double(normalSpeed));
				//currentSpeed.setValue(new Double(normalSpeed.getValue().doubleValue()));
			} else { // Full slow tick
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("SlowTime"), slowTime - stepTime);
			}
		}
	}
}
