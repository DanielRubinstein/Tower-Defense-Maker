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
			if ((projectile.<String>getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue()).equals(STRING_RESOURCES.getFromValueNames("ProjectileType"))) {
				Point2D newPos = calculateNewPos(projectile);
				projectile.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Position"), newPos);
				checkProjectileOutOfBounds(projectile);
				if ((projectile.<Double>getAttribute(STRING_RESOURCES.getFromAttributeNames("ProjectileTraveled")).getValue() + 1.0) >= //1.0 is collision wiggle room
						(projectile.<Double>getAttribute((STRING_RESOURCES.getFromAttributeNames("ProjectileMaxDistance"))).getValue())) {										 
					performProjectileAction(gameData, projectile.<Component>getAttribute("ProjectileTarget").getValue(), projectile);
					toRemove.add(projectile);
				}
			}
		}
		for (Component dead : toRemove) {//avoid ConcurrentMod
			myGameData.getState().getComponentGraph().removeComponent(dead);
		}
	}

	private void checkProjectileOutOfBounds(Component projectile){
		if (myGameData.getState().getTileGrid().getTileByScreenPosition(projectile.<Point2D>getAttribute("Position").getValue())==null){
			toRemove.add(projectile);
			}
	}
	
	private Point2D calculateNewPos(Component c) {
		Double curVel = c.<Double>getAttribute(("Velocity")).getValue();
		Double xVel = curVel;
		Double yVel = curVel;

		Point2D curPos = c.<Point2D>getAttribute(("Position")).getValue();
		Point2D targetPos = c.<Point2D>getAttribute(("ProjectileTargetPosition")).getValue();
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
				c.<Double>getAttribute(("ProjectileTraveled")).getValue() + distTraveled);
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
		List<Component> targetList = gameData.getState().getComponentGraph()
				.getComponentsWithinRadius(target, projectile.<Double>getAttribute("ExplosionRadius").getValue());
		targetList.remove(target);
		if(projectile.getAttribute(STRING_RESOURCES.getFromAttributeNames("FireType")).getValue().equals(STRING_RESOURCES.getFromValueNames("SingleTarget"))){
			targetList.clear();
		}
		targetList.add(target);

		for (Component toHit : targetList) {
			if (toHit.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromValueNames("EnemyType"))) {
				//doing damage to target
				toHit.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Health"), 
						toHit.<Integer>getAttribute(STRING_RESOURCES.getFromAttributeNames("Health")).getValue() - projectile.<Integer>getAttribute(STRING_RESOURCES.getFromAttributeNames("FireDamage")).getValue());
				//slowing target
				if(projectile.<Double>getAttribute(STRING_RESOURCES.getFromAttributeNames("SlowTime")).getValue() > toHit.<Double>getAttribute(STRING_RESOURCES.getFromAttributeNames("SlowTime")).getValue()){
					toHit.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Speed"), projectile.<Double>getAttribute(STRING_RESOURCES.getFromAttributeNames("SlowFactor")).getValue()
						* toHit.<Double>getAttribute(STRING_RESOURCES.getFromAttributeNames("MaxSpeed")).getValue());
				}
				//poisoning target

				toHit.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("PoisonTime"), projectile.<Double>getAttribute(STRING_RESOURCES.getFromAttributeNames("PoisonTime")).getValue());
				toHit.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("PoisonFactor"), projectile.<Double>getAttribute(STRING_RESOURCES.getFromAttributeNames("PoisonFactor")).getValue());
				toRemove.add(projectile);
			}
		}
	}
}