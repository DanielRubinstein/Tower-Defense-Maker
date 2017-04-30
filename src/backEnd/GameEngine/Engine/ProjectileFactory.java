package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactoryImpl;
import backEnd.Attribute.AttributeFactoryReader;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentImpl;
import resources.constants.StringResourceBundle;

/**
 * 
 * @author Christian Martindale
 * creates projectiles using the data from their generating towers
 * for use in AttackEngine
 * Uses resource bundle mapping to Attribute names
 */
public class ProjectileFactory {

	private Component myComponent;
	private Component myTower;
	private StringResourceBundle ATTRIBUTE_RESOURCES = new StringResourceBundle();
	
	@SuppressWarnings("unchecked")
	public ProjectileFactory() throws FileNotFoundException{}
	
	public Component getProjectile(Component tower) throws FileNotFoundException{
		myComponent = new ComponentImpl();
		myTower = tower;
		
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("Type"), ATTRIBUTE_RESOURCES.getFromValueNames("ProjectileType"));
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("ImageFile"), (String) myTower.getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireImage")).getValue());
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireType"), (String) myTower.getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireType")).getValue());
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireDamage"), (Integer)myTower.getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireDamage")).getValue());
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("Velocity"), (Double)myTower.getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("Velocity")).getValue());
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("ProjectileMaxDistance"), 0.0);
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("ProjectileTraveled"), 0.0);
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("ExplosionRadius"), (Double)myTower.getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("ExplosionRadius")).getValue());
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("SlowFactor"), (Double)myTower.getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("SlowFactor")).getValue());
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireRate"), (Double)myTower.getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("FireRate")).getValue());
		myComponent.setAttributeValue(ATTRIBUTE_RESOURCES.getFromAttributeNames("ProjectileTarget"), (String)myTower.getAttribute(ATTRIBUTE_RESOURCES.getFromAttributeNames("ProjectileTarget")).getValue());
		return myComponent;
	}
}

