package backEnd.GameEngine.Behaviors;

import java.util.List;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.Engine;
/**
 * Updates enemy status effects 
 * @author Alex
 *
 */
public class EnemyStatusEngine implements Engine{

	private String ENEMY_TYPE = "Enemy";

	@Override
	public void gameLoop(State currentState, double stepTime) {
		List<Component> components = currentState.getComponentGraph().getAllComponents();
		for(Component component : components){
			if(component.getMyType().equals(ENEMY_TYPE)){
				boolean poisoned = (boolean)component.getAttribute("Poisoned").getValue(); 
				boolean slowed   = component.getAttribute("currentSpeed").getValue() != component.getAttribute("defaultSpeed").getValue();
				if(poisoned){}
				if(slowed){}
			}
		}
		
		
		
		
	}

}
