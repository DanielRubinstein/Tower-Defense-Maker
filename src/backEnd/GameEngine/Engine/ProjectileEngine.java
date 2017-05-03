package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;

/**
 * governs the behavior of projectiles in the State
 * responsible for damaging targets and creating status effects
 * @author Christian Martindale
 *
 */
public class ProjectileEngine implements Engine {
	private GameData myGameData;
	private List<Component> toRemove;
	
	private StringResourceBundle ATTRIBUTE_RESOURCES = new StringResourceBundle();
	private NumericResourceBundle NUMERIC_RESOURCES=new NumericResourceBundle();
	
	//for code readability in the actual methods. Stack Overflow says I can't minimize this. Sorry.
	private String TYPE = ATTRIBUTE_RESOURCES.getFromAttributeNames("Type");
	private String POSITION = ATTRIBUTE_RESOURCES.getFromAttributeNames("Position");
	private String HEALTH = ATTRIBUTE_RESOURCES.getFromAttributeNames("Health");
	private String PROJECTILE_TARGET_POSITION = ATTRIBUTE_RESOURCES.getFromAttributeNames("ProjectileTargetPosition");
	private String SPEED = ATTRIBUTE_RESOURCES.getFromAttributeNames("Speed");
	private String MAX_SPEED = ATTRIBUTE_RESOURCES.getFromAttributeNames("MaxSpeed");
	private String FIRE_TYPE = ATTRIBUTE_RESOURCES.getFromAttributeNames("FireType");
	private String FIRE_DAMAGE = ATTRIBUTE_RESOURCES.getFromAttributeNames("FireDamage");
	private String VELOCITY = ATTRIBUTE_RESOURCES.getFromAttributeNames("Velocity");
	private String PROJECTILE_MAX_DISTANCE = ATTRIBUTE_RESOURCES.getFromAttributeNames("ProjectileMaxDistance");
	private String PROJECTILE_TRAVELED = ATTRIBUTE_RESOURCES.getFromAttributeNames("ProjectileTraveled");
	private String EXPLOSION_RADIUS = ATTRIBUTE_RESOURCES.getFromAttributeNames("ExplosionRadius");
	private String SLOW_FACTOR = ATTRIBUTE_RESOURCES.getFromAttributeNames("SlowFactor");
	private String SLOW_TIME = ATTRIBUTE_RESOURCES.getFromAttributeNames("SlowTime");
	private String POISON_FACTOR = ATTRIBUTE_RESOURCES.getFromAttributeNames("PoisonFactor");
	private String POISON_TIME = ATTRIBUTE_RESOURCES.getFromAttributeNames("PoisonTime");
	private String PROJECTILE_TARGET = ATTRIBUTE_RESOURCES.getFromAttributeNames("ProjectileTarget");
	private String PROJECTILE_TYPE = ATTRIBUTE_RESOURCES.getFromValueNames("ProjectileType");
	private String SINGLE_TARGET = ATTRIBUTE_RESOURCES.getFromValueNames("SingleTarget");
	
	
	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		myGameData = gameData;
		toRemove = new ArrayList<Component>();
		for (Component projectile : myGameData.getState().getComponentGraph().getAllComponents()) {
			if ((projectile.<String>getAttribute(TYPE).getValue()).equals(PROJECTILE_TYPE)) {
				Point2D newPos = calculateNewPos(projectile);
				projectile.setAttributeValue(POSITION, newPos);
				checkProjectileOutOfBounds(projectile);
				if ((projectile.<Double>getAttribute(PROJECTILE_TRAVELED).getValue() + 1.0) >= //1.0 is collision wiggle room
						(projectile.<Double>getAttribute(PROJECTILE_MAX_DISTANCE)).getValue()) {										 
					performProjectileAction(gameData, projectile.<Component>getAttribute(PROJECTILE_TARGET).getValue(), projectile);
					toRemove.add(projectile);
				}
			}
		}
		for (Component dead : toRemove) {//avoid ConcurrentMod
			myGameData.getState().getComponentGraph().removeComponent(dead);
		}
	}

	private void checkProjectileOutOfBounds(Component projectile){
		if (myGameData.getState().getTileGrid().getTileByScreenPosition(projectile.<Point2D>getAttribute(POSITION).getValue()) == null){
			toRemove.add(projectile);
			}
	}
	
	/**
	 * Uses the projectile's velocity Attribute to calculate its new position
	 * @param projectile a Component representing a projectile
	 * @return a Point2D representing the projectile's new position on the ComponentGraph
	 */
	private Point2D calculateNewPos(Component projectile) {
		Double curVel = projectile.<Double>getAttribute(VELOCITY).getValue();
		Double xVel = curVel;
		Double yVel = curVel;

		Point2D curPos = projectile.<Point2D>getAttribute(POSITION).getValue();
		Point2D targetPos = projectile.<Point2D>getAttribute(PROJECTILE_TARGET_POSITION).getValue();
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
		projectile.setAttributeValue(PROJECTILE_TRAVELED,
				projectile.<Double>getAttribute(PROJECTILE_TRAVELED).getValue() + distTraveled);
		return newPos;
	}

	/**
	 * Upon collision, takes action on the target based on the projectile's
	 * Attributes
	 * currently supports hasting, slowing, and poison/burn
	 * @param projectile the projectile performing the action
	 * @param target the target of the projectile's action (usually an enemy)
	 */
	private void performProjectileAction(GameData gameData, Component target, Component projectile) {
		List<Component> targetList = gameData.getState().getComponentGraph()
				.getComponentsWithinRadius(target, projectile.<Double>getAttribute(EXPLOSION_RADIUS).getValue());
		targetList.remove(target);
		if(projectile.getAttribute(FIRE_TYPE).getValue().equals(SINGLE_TARGET)){
			targetList.clear();
		}
		targetList.add(target);
		System.out.println("targetList size is " + targetList.size());

		for (Component toHit : targetList) {
			if ((target.getAttribute(TYPE).getValue().equals(toHit.getAttribute(TYPE).getValue()))) {
				toHit.setAttributeValue(HEALTH, toHit.<Integer>getAttribute(HEALTH).getValue() - 
						projectile.<Integer>getAttribute(FIRE_DAMAGE).getValue());
				target.setAttributeValue("Size", (target.<Double>getAttribute("Size").getValue())*NUMERIC_RESOURCES.getFromSizing("ComponentReductionFactor"));
				if(projectile.<Double>getAttribute(SLOW_TIME).getValue() > toHit.<Double>getAttribute(SLOW_TIME).getValue()){
					toHit.setAttributeValue(SPEED, projectile.<Double>getAttribute(SLOW_FACTOR).getValue()
						* toHit.<Double>getAttribute(MAX_SPEED).getValue());
				}
				toHit.setAttributeValue(POISON_TIME, projectile.<Double>getAttribute(POISON_TIME).getValue());
				toHit.setAttributeValue(POISON_FACTOR, projectile.<Double>getAttribute(POISON_FACTOR).getValue());
				toRemove.add(projectile);
			}
		}
	}
}