package backEnd.GameEngine.Engine;

import java.util.Collection;
import java.util.List;

import backEnd.Attribute.Attribute;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;

import backEnd.GameEngine.Engine.Status.StatusEffect;
import resources.constants.StringResourceBundle;

/**
 * Updates enemy status effects
 * 
 * @author Alex
 *
 */
public class EnemyStatusEngine implements Engine {

	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();
	private String ENEMY_TYPE = STRING_RESOURCES.getFromValueNames("EnemyType");

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		Collection<Component> components = gameData.getState().getComponentGraph().getAllComponents();
		for (Component component : components) {
			if (component.getMyType().equals(ENEMY_TYPE)) {
				updatePoison(stepTime, component);
				updateSlowed(stepTime, component);
			}
		}
	}

	private void updatePoison(double stepTime, Component component) {
		Attribute<StatusEffect> poisoned = component.getAttribute(STRING_RESOURCES.getFromAttributeNames("PoisonTime"));
		Attribute<Double> health = component.getAttribute(STRING_RESOURCES.getFromAttributeNames("Health"));
		Double poisonTime = poisoned.getValue().getTime();
		if (poisonTime > 0) {
			if (poisonTime - stepTime < 0) { // If poison should only do partial
												// tick
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Poison"), new StatusEffect(new Double(0), new Double(0)));
				//poisoned.setValue(new StatusEffect(new Double(0), new Double(0)));
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Health"), health.getValue() - poisoned.getValue().getFactor() * poisonTime);
				//health.setValue(health.getValue() - poisoned.getValue().getFactor() * poisonTime);
			} else { // Full poison tick
				
				poisoned.getValue().setTime(poisonTime - stepTime); //TODO FIX THIS, SETTIME NOT OBSERVABLE
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Health"), health.getValue() - poisoned.getValue().getFactor() * stepTime);
				//health.setValue(health.getValue() - poisoned.getValue().getFactor() * stepTime);
			}
		}
	}

	private void updateSlowed(double stepTime, Component component) {
		Attribute<StatusEffect> slowed = component.getAttribute("SlowTime");
		Attribute<Double> currentSpeed = component.getAttribute("CurrentSpeed");
		Attribute<Double> normalSpeed = component.getAttribute("NormalSpeed");

		Double poisonTime = slowed.getValue().getTime();
		if (poisonTime > 0) {
			if (poisonTime - stepTime < 0) { // If slow should only do partial
												// tick
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("SlowTime"), new StatusEffect(new Double(0), new Double(0)));
				//slowed.setValue(new StatusEffect(new Double(0), new Double(0)));
				component.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("CurrentSpeed"), new Double(normalSpeed.getValue().doubleValue()));
				//currentSpeed.setValue(new Double(normalSpeed.getValue().doubleValue()));
			} else { // Full slow tick
				slowed.getValue().setTime(poisonTime - stepTime);
			}
		}
	}
}
