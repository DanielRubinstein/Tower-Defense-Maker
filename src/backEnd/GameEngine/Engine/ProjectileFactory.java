package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactoryImpl;
import backEnd.Attribute.AttributeFactoryReader;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.ComponentImpl;

/**
 * 
 * @author Christian Martindale
 * creates projectiles using the data from their generating towers
 * for use in AttackEngine
 */
public class ProjectileFactory {

	private AttributeData myAttributeData;
	private AttributeFactoryReader myAttributeFactory;
	private ComponentImpl myComponent;
	
	@SuppressWarnings("unchecked")
	public ProjectileFactory(ComponentImpl tower) throws FileNotFoundException{
		myComponent=new ComponentImpl();
		myAttributeFactory = new AttributeFactoryImpl();
		AttributeData ad = new AttributeData();
		//TODO clean up code
		Attribute<String> myType = (Attribute<String>) myAttributeFactory.getAttribute(("Type"));
		Attribute<String> projectileImage = (Attribute<String>) myAttributeFactory.getAttribute(("ImageFile"));
		Attribute<String> projectileType = (Attribute<String>) myAttributeFactory.getAttribute(("FireType"));
		Attribute<Integer> projectileHealth = (Attribute<Integer>) myAttributeFactory.getAttribute(("Health"));
		Attribute<Integer> projectileDamage = (Attribute<Integer>) myAttributeFactory.getAttribute(("FireDamage"));
		Attribute<Double> projectileVelocity = (Attribute<Double>) myAttributeFactory.getAttribute(("Velocity"));
		Attribute<Double> projectileMaxDist = (Attribute<Double>) myAttributeFactory.getAttribute(("ProjectileMaxDistance"));
		Attribute<Double> projectileTraveled = (Attribute<Double>) myAttributeFactory.getAttribute(("ProjectileTraveled"));
		Attribute<Double> explosionSize = (Attribute<Double>) myAttributeFactory.getAttribute(("ExplosionRadius"));
		Attribute<Double> slowFactor = (Attribute<Double>) myAttributeFactory.getAttribute(("SlowFactor"));
		Attribute<Double> fireRate = (Attribute<Double>) myAttributeFactory.getAttribute(("FireRate"));
		Attribute<String> target = (Attribute<String>) myAttributeFactory.getAttribute(("ProjectileTarget"));

		myType.setValue("Projectile"); //surprisingly, all projectiles are of type Projectile!
		projectileImage.setValue((String) tower.getAttribute("FireImage").getValue());
		projectileType.setValue((String) tower.getAttribute("FireType").getValue());
		projectileHealth.setValue((Integer) tower.getAttribute("Health").getValue());
		projectileDamage.setValue((Integer) tower.getAttribute("FireDamage").getValue());
		projectileMaxDist.setValue(0.0);
		projectileTraveled.setValue(0.0);
		projectileVelocity.setValue((Double) tower.getAttribute("Velocity").getValue());
		explosionSize.setValue((Double) tower.getAttribute("ExplosionRadius").getValue());
		slowFactor.setValue((Double) tower.getAttribute("SlowFactor").getValue());
		fireRate.setValue((Double) tower.getAttribute("FireRate").getValue());
		target.setValue((String) tower.getAttribute("ProjectileTarget").getValue());

		myComponent.setAttributeValue(("Type"), (String) myType.getValue());
		myComponent.setAttributeValue(("ImageFile"), (String) projectileImage.getValue());
		myComponent.setAttributeValue(("FireType"), (String) projectileType.getValue());
		myComponent.setAttributeValue(("Health"), (Integer) projectileHealth.getValue());
		myComponent.setAttributeValue(("FireDamage"), (Integer) projectileDamage.getValue());
		myComponent.setAttributeValue(("ProjectileMaxDistance"), (Double) projectileMaxDist.getValue());
		myComponent.setAttributeValue(("ProjectileTraveled"), (Double) projectileTraveled.getValue());
		myComponent.setAttributeValue(("Velocity"), (Double) projectileVelocity.getValue());
		myComponent.setAttributeValue(("ExplosionRadius"), (Double) explosionSize.getValue());
		myComponent.setAttributeValue(("SlowFactor"), (Double) slowFactor.getValue());
		myComponent.setAttributeValue(("FireRate"), (Double) fireRate.getValue());
		myComponent.setAttributeValue(("ProjectileTarget"), (String) target.getValue());
		
		myAttributeData = ad;
	}

	public AttributeData getMyAttributeData() {
		return myAttributeData;
	}
	
	public ComponentImpl getProjectile(){
		return myComponent;
	}
}
