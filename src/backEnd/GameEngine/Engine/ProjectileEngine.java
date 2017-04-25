package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import javafx.geometry.Point2D;

/**
 * governs the behavior of projectiles in the State
 * 
 * @author Christian Martindale
 * @author Daniel
 *
 */
public class ProjectileEngine implements Engine {
	private GameData myGameData;
	private List<Component> toRemove;

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		myGameData = gameData;
		toRemove = new ArrayList<Component>();
		for (Component c : myGameData.getState().getComponentGraph().getAllComponents()) {
			if (((String) c.getAttribute("Type").getValue()).equals("Projectile")) {
				Point2D newPos = calculateNewPos(c);
				Tile currentTile = myGameData.getState().getTileGrid().getTileByScreenPosition(newPos);
				if (currentTile == null) {
					toRemove.add(c);
					continue;
				}
				c.setAttributeValue("Position", newPos);
				//System.out.println("traveledDist is " + (Double) c.getAttribute("ProjectileTraveled").getValue());
				//System.out.println("maxDist is " + (Double) c.getAttribute("ProjectileMaxDistance").getValue());
				if (((Double) c.getAttribute("ProjectileTraveled").getValue() + 1.0) >= (Double) c //1.0 is to allow for wiggle room
						.getAttribute(("ProjectileMaxDistance")).getValue()) {
					//TODO: may have issues when the target is already destroyed before it gets there
					//System.out.println("about to perform projectile actions");
					performProjectileAction(gameData, (Component)c.getAttribute("ProjectileTarget").getValue(), c);


					toRemove.add(c);

				}

			}
		}
		for (Component c : toRemove) {
			myGameData.getState().getComponentGraph().removeComponent(c);
		}

	}

	private Point2D calculateNewPos(Component c) {
		Double curVel = (Double) c.getAttribute(("Velocity")).getValue();
		
		//TODO: CHRISTIAN targeting is screwed up for going backwards - probably in slope (if not, then error
		//might be in the target point initialization in AttackEngine (.subtract?)
		// CHRISTIAN make sure the projectiles and targets are getting from the list that it's looping over 

		Point2D curPos = (Point2D) c.getAttribute(("Position")).getValue();
		Point2D targetPos = (Point2D) c.getAttribute(("ProjectileTargetPosition")).getValue();
		System.out.println("Projectile Target Point is " + targetPos);
		Point2D difference = targetPos.subtract(curPos);

		System.out.print("Difference is " + difference);
		Double slope = difference.getY() / difference.getX();
		System.out.println("slope is " + slope);
		Double distTraveled = Math.sqrt(Math.pow(curVel, 2) + Math.pow(slope * curVel, 2));
		Point2D newPos = new Point2D(curPos.getX() + curVel, curPos.getY() + curVel * slope);
		c.setAttributeValue("ProjectileTraveled",
				((Double) c.getAttribute(("ProjectileTraveled")).getValue()) + distTraveled);
		return newPos;
	}

	/**
	 * Upon collision, takes action on the target based on the projectile's
	 * Attributes
	 * 
	 * @param projectile the projectile performing the action
	 * @param target the object of the projectile's action (usually an enemy)
	 */
	private void performProjectileAction(GameData gameData, Component target, Component projectile) {

		List<Component> targetList = (ArrayList<Component>) gameData.getState().getComponentGraph()
				.getComponentsWithinRadius(target, (Double) projectile.getAttribute("ExplosionRadius").getValue());
		targetList.add(target);
		//System.out.println("targetList size is " + targetList.size());
		
		for (Component toHit : targetList) {
			//System.out.println("Target looping has begun");

			toHit.setAttributeValue("Health", (Integer) toHit.getAttribute("Health").getValue()
					- (Integer) projectile.getAttribute("FireDamage").getValue());
			//System.out.println("should have reduced HP to " + toHit.getAttribute("Health").getValue());
			toHit.setAttributeValue("Velocity", ((Double) projectile.getAttribute("SlowFactor").getValue()
					* (Double) toHit.getAttribute("Speed").getValue()));
			gameData.getState().getComponentGraph().removeComponent(projectile);
			if (projectile.getAttribute("FireType").getValue().equals("SingleTarget")) {
				break; // if AOE, continue to loop through all targets, else
						// only affect one target, needs testing
			}
		}
		

	}
}
