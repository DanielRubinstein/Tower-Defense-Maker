package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentImpl;
import resources.constants.StringResourceBundle;

/**
 * 
 * @author Christian Martindale
 * creates projectiles using the data from their generating towers
 * for use in AttackEngine
 */
public class ProjectileFactory {

	private Component myComponent;
	private Component myTower;
	private StringResourceBundle ATTRIBUTE_RESOURCES = new StringResourceBundle();
	
	private String TYPE = ATTRIBUTE_RESOURCES.getFromAttributeNames("Type");
	private String IMAGE_FILE = ATTRIBUTE_RESOURCES.getFromAttributeNames("ImageFile");
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
	private String FIRE_RATE = ATTRIBUTE_RESOURCES.getFromAttributeNames("FireRate");
	private String PROJECTILE_TARGET = ATTRIBUTE_RESOURCES.getFromAttributeNames("ProjectileTarget");
	
	

	public ProjectileFactory() throws FileNotFoundException{}
	
	public Component getProjectile(Component tower) throws FileNotFoundException{
		myComponent = new ComponentImpl();
		myTower = tower;
		
		myComponent.setAttributeValue(TYPE, ATTRIBUTE_RESOURCES.getFromValueNames("ProjectileType"));
		myComponent.setAttributeValue(IMAGE_FILE, myTower.<String>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireImage")).getValue());
		myComponent.setAttributeValue(FIRE_TYPE, myTower.<String>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireType")).getValue());
		myComponent.setAttributeValue(PROJECTILE_TARGET, myTower.<Component>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("ProjectileTarget")).getValue());
		myComponent.setAttributeValue(FIRE_DAMAGE, myTower.<Integer>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireDamage")).getValue());
		myComponent.setAttributeValue(VELOCITY, myTower.<Double>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("Velocity")).getValue());
		myComponent.setAttributeValue(PROJECTILE_MAX_DISTANCE, 0.0);
		myComponent.setAttributeValue(PROJECTILE_TRAVELED, 0.0);
		myComponent.setAttributeValue(EXPLOSION_RADIUS, myTower.<Double>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("ExplosionRadius")).getValue());
		myComponent.setAttributeValue(SLOW_FACTOR, myTower.<Double>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("SlowFactor")).getValue());
		myComponent.setAttributeValue(SLOW_TIME, myTower.<Double>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("SlowTime")).getValue());
		myComponent.setAttributeValue(POISON_FACTOR, myTower.<Double>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("PoisonFactor")).getValue());
		myComponent.setAttributeValue(POISON_TIME, myTower.<Double>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("PoisonTime")).getValue());
		myComponent.setAttributeValue(FIRE_RATE, myTower.<Double>getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireRate")).getValue());
		return myComponent;
	}
}

