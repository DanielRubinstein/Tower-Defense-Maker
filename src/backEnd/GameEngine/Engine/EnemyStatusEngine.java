package backEnd.GameEngine.Engine;

import java.util.Collection;
import java.util.List;

import backEnd.Attribute.Attribute;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;

import backEnd.GameEngine.Engine.Status.StatusEffect;

/**
 * Updates enemy status effects
 * 
 * @author Alex
 *
 */
public class EnemyStatusEngine implements Engine {

	private String ENEMY_TYPE = "Enemy";

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
		Attribute<StatusEffect> poisoned = component.getAttribute("Poisoned");
		Attribute<Double> health = component.getAttribute("Health");
		Double poisonTime = poisoned.getValue().getTime();
		if (poisonTime > 0) {
			if (poisonTime - stepTime < 0) { // If poison should only do partial
												// tick
				poisoned.setValue(new StatusEffect(new Double(0), new Double(0)));
				health.setValue(health.getValue() - poisoned.getValue().getFactor() * poisonTime);
			} else { // Full poison tick
				poisoned.getValue().setTime(poisonTime - stepTime);
				health.setValue(health.getValue() - poisoned.getValue().getFactor() * stepTime);
			}
		}
	}

	private void updateSlowed(double stepTime, Component component) {
		Attribute<StatusEffect> slowed = component.getAttribute("Slowed");
		Attribute<Double> currentSpeed = component.getAttribute("CurrentSpeed");
		Attribute<Double> normalSpeed = component.getAttribute("NormalSpeed");

		Double poisonTime = slowed.getValue().getTime();
		if (poisonTime > 0) {
			if (poisonTime - stepTime < 0) { // If poison should only do partial
												// tick
				slowed.setValue(new StatusEffect(new Double(0), new Double(0)));
				currentSpeed.setValue(new Double(normalSpeed.getValue().doubleValue()));
			} else { // Full poison tick
				slowed.getValue().setTime(poisonTime - stepTime);
			}
		}
	}
}
