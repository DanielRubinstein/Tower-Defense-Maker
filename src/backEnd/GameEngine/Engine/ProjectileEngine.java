package backEnd.GameEngine.Engine;

import java.util.List;
import java.util.ResourceBundle;

import backEnd.GameData.GameData;
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
	public void gameLoop(GameData gameData, double stepTime) { //should refactor the movement into separate method if time
		myState = gameData.getState();
		
		for (Component c: myState.getComponentGraph().getAllComponents()){
			if(c.getMyType().equals("Projectile")){
				Point2D newPos = calculateNewPos(c);
				
				if((Double)c.getAttribute("ProjectileTraveled").getValue() >= (Double)c.getAttribute(myResources.getString("ProjectileMaxDistance")).getValue()){								
					List<Component> targets = gameData.getState().getComponentGraph().getComponentsWithinRadius(c, (Double)c.getAttribute("ExplosionRadius").getValue());
					performProjectileAction(c,targets);
					
					myState.getComponentGraph().removeComponent(c); //reached destination, delete projectile
					continue;
				}
				c.setAttributeValue(myResources.getString("Position"), newPos);			
			}
		}	
	}
	
	private Point2D calculateNewPos(Component c){
		double curVel = (Double) c.getAttribute(myResources.getString("Velocity")).getValue();		
		Point2D curPos = (Point2D) c.getAttribute(myResources.getString("Position")).getValue();
		Point2D targetPos = (Point2D) c.getAttribute(myResources.getString("ProjectileTargetPosition")).getValue();
		Point2D difference = targetPos.subtract(curPos);
		
		double slope = difference.getY() / difference.getX();
		double distTraveled = Math.sqrt(Math.pow(curVel, 2) + Math.pow(slope*curVel, 2));
		Point2D newPos = curPos.add((curVel), curVel*slope);	
		c.setAttributeValue(myResources.getString("ProjectileTraveled"), c.getAttribute(myResources.getString("ProjectileTraveled") + distTraveled));
		
		return newPos;
		
	}

	/**
	 * Upon collision, takes action on the target based on the projectile's Attributes
	 * 
	 * @param projectile the projectile performing the action
	 * @param target the object of the projectile's action (usually an enemy)
	 */
	private void performProjectileAction(Component projectile, List<Component> targetList) {
		for(Component target : targetList){
			target.setAttributeValue("Health", (Integer)projectile.getAttribute("ProjectileDamage").getValue());
			target.setAttributeValue("Velocity", ((Double)projectile.getAttribute("SlowFactor").getValue() * (Double)target.getAttribute("Speed").getValue()));
		}
	}
}
