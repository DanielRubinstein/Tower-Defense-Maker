package backEnd.GameEngine.Engine;

import java.util.ResourceBundle;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import javafx.geometry.Point2D;

/**
 * governs the behavior of projectiles in the game
 * @author Christian Martindale
 *
 */
public class ProjectileEngine implements Engine{

	private State myState;
	private final static String RESOURCES_PATH = "resources/attributes";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	
	
	@Override
	public void gameLoop(State currentState, double stepTime) {
		myState = currentState;
		
		for (Component c: myState.getComponentGraph().getAllComponents()){
			if(c.getMyType().equals("Projectile")){
				double curVel = (double) c.getAttribute(myResources.getString("Velocity")).getValue();		
				Point2D curPos = (Point2D) c.getAttribute(myResources.getString("Position")).getValue();
				Point2D targetPos = (Point2D) c.getAttribute(myResources.getString("ProjectileTargetPosition")).getValue();
				Point2D difference = targetPos.subtract(curPos);
				double slope = difference.getY() / difference.getX();
				double distTraveled = Math.sqrt(Math.pow(curVel, 2) + Math.pow(slope*curVel, 2));
				
				Point2D newPos = curPos.add((curVel), curVel*slope);
				c.setAttributeValue(myResources.getString("ProjectileTraveled"), c.getAttribute(myResources.getString("ProjectileTraveled") + distTraveled));
				double traveledSoFar = (double)c.getAttribute(myResources.getString("ProjectileTraveled")).getValue();
				
				if(traveledSoFar >= (double)c.getAttribute(myResources.getString("ProjectileDistance")).getValue()){
					myState.getComponentGraph().removeComponent(c); //reached destination, remove projectile
					continue;
				}
				c.setAttributeValue(myResources.getString("Position"), newPos);
				
			}
		}
		
	}

	
	
}
