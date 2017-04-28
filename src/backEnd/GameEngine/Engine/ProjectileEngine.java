package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.GameData.GameData;
import backEnd.GameData.State.ComponentImpl;
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
	private List<ComponentImpl> toRemove;

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		myGameData = gameData;
		toRemove = new ArrayList<ComponentImpl>();
		for (ComponentImpl c : myGameData.getState().getComponentGraph().getAllComponents()) {
			if (((String) c.getAttribute("Type").getValue()).equals("Projectile")) {
				Point2D newPos = calculateNewPos(c);
				c.setAttributeValue("Position", newPos);
				checkProjectileOutOfBounds(c);
				if (((Double) c.getAttribute("ProjectileTraveled").getValue() + 1.0) >= (Double) c //1.0 is wiggle room
						.getAttribute(("ProjectileMaxDistance")).getValue()) {
					// TODO: may have issues when the target is already
					// destroyed before it gets there
					// System.out.println("about to perform projectile
					// actions");
					performProjectileAction(gameData, (ComponentImpl) c.getAttribute("ProjectileTarget").getValue(), c);
					toRemove.add(c);

				}

			}
		}
		System.out.println("About to remove stuff from graph" );
		for (ComponentImpl c : toRemove) {
			myGameData.getState().getComponentGraph().removeComponent(c);
		}

	}

	private void checkProjectileOutOfBounds(ComponentImpl projectile){
		if (myGameData.getState().getTileGrid().getTileByScreenPosition((Point2D) projectile.getAttribute("Position").getValue())==null){
			toRemove.add(projectile);
			}
	}
	
	private Point2D calculateNewPos(ComponentImpl c) {
		Double curVel = (Double) c.getAttribute(("Velocity")).getValue();
		Double xVel = curVel;
		Double yVel = curVel;

		// TODO:
		// CHRISTIAN make sure the projectiles and targets are getting from the
		// list that it's looping over

		Point2D curPos = (Point2D) c.getAttribute(("Position")).getValue();
		Point2D targetPos = (Point2D) c.getAttribute(("ProjectileTargetPosition")).getValue();
		Point2D difference = targetPos.subtract(curPos);
		Double slope = difference.getY() / difference.getX();
		
		if (difference.getX() < 0) {
			xVel *= -1;
		}
		
		if (difference.getY() < 0) {
			yVel *= -1;
		}
		
		Double distTraveled = Math.sqrt(Math.pow(curVel, 2) + Math.pow(slope * curVel, 2));
		Point2D newPos = new Point2D(curPos.getX() + xVel, curPos.getY() + yVel * Math.abs(slope));
		c.setAttributeValue("ProjectileTraveled",
				((Double) c.getAttribute(("ProjectileTraveled")).getValue()) + distTraveled);
		return newPos;
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
	private void performProjectileAction(GameData gameData, ComponentImpl target, ComponentImpl projectile) {

		List<ComponentImpl> targetList = (ArrayList<ComponentImpl>) gameData.getState().getComponentGraph()
				.getComponentsWithinRadius(target, (Double) projectile.getAttribute("ExplosionRadius").getValue());
		targetList.add(target);

		for (ComponentImpl toHit : targetList) {
			// System.out.println("Target looping has begun");
			if (toHit.getAttribute("Type").getValue().equals("Enemy")) {
				toHit.setAttributeValue("Health", (Integer) toHit.getAttribute("Health").getValue() - (Integer) projectile.getAttribute("FireDamage").getValue());
				System.out.println("should have reduced HP to " + toHit.getAttribute("Health").getValue() + " type is " + toHit.getAttribute("Type").getValue());
				toHit.setAttributeValue("Velocity", ((Double) projectile.getAttribute("SlowFactor").getValue() * (Double) toHit.getAttribute("Speed").getValue()));
				toRemove.add(projectile);
				if (projectile.getAttribute("FireType").getValue().equals("SingleTarget")) {
					break; // if AOE, continue to loop through all targets, else
							// only affect one target, needs testing
				}
			}
		}

	}
}
