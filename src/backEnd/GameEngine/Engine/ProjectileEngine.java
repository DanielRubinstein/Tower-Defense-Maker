package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import javafx.geometry.Point2D;

/**
 * governs the behavior of projectiles in the State
 * 
 * @author Christian Martindale
 *
 */
public class ProjectileEngine implements Engine {

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		//System.out.println(this.getClass().getSimpleName() + ": ProjectileEngine called");
		
		List<Component> toRemove=new ArrayList<Component>();
		for (Component c : gameData.getState().getComponentGraph().getAllComponents()) {
			if (((String) c.getAttribute("Type").getValue()).equals("Projectile")) {
				//System.out.println("Calculating projectile position");

				calculateNewPos(c);
				System.out.println("projectileTraveled is " + c.getAttribute("ProjectileTraveled").getValue());
				System.out.println("projectileMaxDist is " + c.getAttribute("ProjectileMaxDistance").getValue());
				
				if ((Double) c.getAttribute("ProjectileTraveled").getValue() >= (Double) c.getAttribute(("ProjectileMaxDistance")).getValue()) {
					System.out.println("Projectile has reached target");
					List<Component> targets = gameData.getState().getComponentGraph().getComponentsWithinRadius(c,
							(Double) c.getAttribute("ExplosionRadius").getValue());
					System.out.println("About to perform projectile action");
					performProjectileAction(c, targets);

					toRemove.add(c);
					//gameData.getState().getComponentGraph().removeComponent(c);  reached
																	// destination,
																	// will cause ConcModException probably
					continue;
				}
				//c.setAttributeValue("Position", newPos);
			}
		}
		for(Component c:toRemove){
			gameData.getState().getComponentGraph().removeComponent(c);
		}

	}

	private void calculateNewPos(Component c) {
		Double curVel = (Double) c.getAttribute(("Velocity")).getValue();
		
		Point2D curPos = (Point2D) c.getAttribute(("Position")).getValue();
		Point2D targetPos = (Point2D) c.getAttribute(("ProjectileTargetPosition")).getValue();
		Point2D difference = targetPos.subtract(curPos);

		Double slope = difference.getY() / difference.getX();
		Double distTraveled = Math.sqrt(Math.pow(curVel, 2) + Math.pow(slope * curVel, 2));
		Point2D newPos = new Point2D(curPos.getX()+curVel, curPos.getY()+curVel* slope);
		//System.out.println("curPos.get(X) is "+curPos.getX()+" , curPos.get(Y) is "+curPos.getY()+" , curVel is: "+curVel+" ,slope is "+slope);
		//curPos.add((curVel), curVel * slope);
		c.setAttributeValue("ProjectileTraveled",((Double) c.getAttribute(("ProjectileTraveled")).getValue()) + distTraveled);
		c.setAttributeValue("Position", newPos);
		//System.out.println("Slope is " + slope + "distanceTraveled" + distTraveled);
		//System.out.println("performed targeting math" + newPos + curPos + targetPos + difference);

	}

	/**
	 * Upon collision, takes action on the target based on the projectile's
	 * Attributes
	 * 
	 * @param projectile
	 *            the projectile performing the action
	 * @param target
	 *            the object of the projectile's action (usually an enemy)
	 */
	private void performProjectileAction(Component projectile, List<Component> targetList) {

			for (Component target : targetList) {
				System.out.println(targetList.size());
				System.out.println(target.getAttribute("Target health is " + target.getAttribute("Health").getValue()));
				target.setAttributeValue("Health", (Integer)target.getAttribute("Health").getValue() - (Integer) projectile.getAttribute("ProjectileDamage").getValue());
				target.setAttributeValue("Velocity", ((Double) projectile.getAttribute("SlowFactor").getValue()
						* (Double) target.getAttribute("Speed").getValue()));
				System.out.println("projectile action performed");
				if(projectile.getAttribute("FireType").getValue().equals("SingleTarget")){
					System.out.println("Single Target, loop broken, action finished");
					break; //if AOE, continue to loop through all targets, else only affect one target
				}
			}
		
	}
}
