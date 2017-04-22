package backEnd.GameEngine.Engine;

import java.util.List;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
/**
 * Updates enemy status effects 
 * @author Alex
 *
 */
public class EnemyStatusEngine implements Engine{

	private String ENEMY_TYPE = "Enemy";

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		List<Component> components = gameData.getState().getComponentGraph().getAllComponents();
		for(Component component : components){
			if(component.getMyType().equals(ENEMY_TYPE)){
				boolean poisoned = (boolean)component.getAttribute("Poisoned").getValue(); 
				boolean slowed   = component.getAttribute("currentSpeed").getValue() != component.getAttribute("defaultSpeed").getValue();
				if(poisoned){
					
				}
				if(slowed){
					
				}
				if(frozen){
					
				}
			}
		}
		
		
		
		
	}

}
