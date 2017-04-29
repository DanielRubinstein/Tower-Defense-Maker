package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.List;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;

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
	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		myGameData = gameData;
		toRemove = new ArrayList<Component>();
		for (Component projectile : myGameData.getState().getComponentGraph().getAllComponents()) {
			if (((String) projectile.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue()).equals(STRING_RESOURCES.getFromValueNames("ProjectileType"))) {
				Point2D newPos = calculateNewPos(projectile);
				projectile.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Position"), newPos);
				checkProjectileOutOfBounds(projectile);
				if (((Double) projectile.getAttribute(STRING_RESOURCES.getFromAttributeNames("ProjectileTraveled")).getValue() + 1.0) >= (Double) projectile //1.0 is wiggle room
						.getAttribute((STRING_RESOURCES.getFromAttributeNames("ProjectileMaxDistance"))).getValue()) {										 //to account for division error
					performProjectileAction(gameData, (Component) projectile.getAttribute("ProjectileTarget").getValue(), projectile);
					toRemove.add(projectile);
				}
			}
		}
		for (Component dead : toRemove) {
			myGameData.getState().getComponentGraph().removeComponent(dead);
		}
	}

	private void checkProjectileOutOfBounds(Component projectile){
		if (myGameData.getState().getTileGrid().getTileByScreenPosition((Point2D) projectile.getAttribute("Position").getValue())==null){
			toRemove.add(projectile);
			}
	}
	
	private Point2D calculateNewPos(Component c) {
		Double curVel = (Double) c.getAttribute(("Velocity")).getValue();
		Double xVel = curVel;
		Double yVel = curVel;

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
	private void performProjectileAction(GameData gameData, Component target, Component projectile) {
		List<Component> targetList = (ArrayList<Component>) gameData.getState().getComponentGraph()
				.getComponentsWithinRadius(target, (Double) projectile.getAttribute("ExplosionRadius").getValue());
		targetList.remove(target);
		System.out.println("ProjectileEngine FireType is " + projectile.getAttribute("FireType").getValue());
		if(projectile.getAttribute(STRING_RESOURCES.getFromAttributeNames("FireType")).getValue().equals(STRING_RESOURCES.getFromAttributeNames("SingleTarget"))){
			targetList.clear();
			System.out.println("TargetList cleared ProjectileEngine");
		}
		targetList.add(target);
		System.out.println("Targetlist size is " + targetList.size());

		for (Component toHit : targetList) {
			if (toHit.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromValueNames("EnemyType"))) {
				//doing damage to target
				toHit.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Health"), 
						(Integer) toHit.getAttribute(STRING_RESOURCES.getFromAttributeNames("Health")).getValue() - (Integer) projectile.getAttribute(STRING_RESOURCES.getFromAttributeNames("FireDamage")).getValue());
				//slowing target
				toHit.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Velocity"), ((Double) projectile.getAttribute(STRING_RESOURCES.getFromAttributeNames("SlowFactor")).getValue()
						* (Double) toHit.getAttribute(STRING_RESOURCES.getFromAttributeNames("Speed")).getValue()));
				toRemove.add(projectile);
			}
		}
	}
}
