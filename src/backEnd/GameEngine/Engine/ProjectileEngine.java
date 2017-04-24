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
				Tile currentTile = myGameData.getState().getTileGrid().getTileByScreenLocation(newPos);
				if (currentTile == null) {
					toRemove.add(c);
					continue;
				}
				c.setAttributeValue("Position", newPos);
				if ((Double) c.getAttribute("ProjectileTraveled").getValue() >= (Double) c
						.getAttribute(("ProjectileMaxDistance")).getValue()) {
					List<Component> targets = (ArrayList<Component>) myGameData.getState().getComponentGraph()
							.getComponentsWithinRadius(c, (Double) c.getAttribute("ExplosionRadius").getValue());
					performProjectileAction(c, targets);

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

		Point2D curPos = (Point2D) c.getAttribute(("Position")).getValue();
		Point2D targetPos = (Point2D) c.getAttribute(("ProjectileTargetPosition")).getValue();
		Point2D difference = targetPos.subtract(curPos);

		Double slope = difference.getY() / difference.getX();
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
	 * @param projectile
	 *            the projectile performing the action
	 * @param target
	 *            the object of the projectile's action (usually an enemy)
	 */
	private void performProjectileAction(Component projectile, List<Component> targetList) {
		for (Component target : targetList) {
			if (! ((String)target.getAttribute("Type").getValue()).equals("Enemy")){
				System.out.println("Target isn't an enemy");
			}
			//System.out.println(targetList.size() + "LIST SIZE SKIRT SKIRT");
			target.setAttributeValue("Health", (Integer) target.getAttribute("Health").getValue()
					- (Integer) projectile.getAttribute("FireDamage").getValue());
			target.setAttributeValue("Velocity", ((Double) projectile.getAttribute("SlowFactor").getValue()
					* (Double) target.getAttribute("Speed").getValue()));
			//System.out.println("projectile action performed");
			myGameData.getState().getComponentGraph().removeComponent(projectile);
			if (projectile.getAttribute("FireType").getValue().equals("SingleTarget")) {
				break; // if AOE, continue to loop through all targets, else
						// only affect one target
			}
		}

	}
}
