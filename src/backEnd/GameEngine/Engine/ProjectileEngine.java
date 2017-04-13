package backEnd.GameEngine.Engine;

import java.util.ResourceBundle;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;

public class ProjectileEngine implements Engine{

	private State myState;
	private final static String RESOURCES_PATH = "resources/attributes";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	
	
	@Override
	public void gameLoop(State currentState, double stepTime) {
		myState = currentState;
		
		for (Component c: myState.getComponentGraph().getAllComponents()){
			if(c.getMyType().equals("Projectile")){
				double curXVel = (double) c.getAttribute(myResources.getString("XVelocity")).getValue();
				double curYVel = (double) c.getAttribute(myResources.getString("YVelocity")).getValue();
				
				c.setAttributeValue(myResources.getString("Position"), c.getAttribute(myResources.getString("Position")).getValue());
				
			}
		}
		
	}

	
	
}
