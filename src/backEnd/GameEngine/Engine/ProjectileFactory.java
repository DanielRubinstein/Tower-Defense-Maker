package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactory;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.Component;

/**
 * 
 * @author Christian Martindale
 * creates projectiles using the data from their generating towers
 * for use in AttackEngine
 */
public class ProjectileFactory {

	private AttributeData myAttributeData;
	private AttributeFactory myAttributeFactory;
	private Component myComponent;
	
	@SuppressWarnings("unchecked")
	public ProjectileFactory(Component tower) throws FileNotFoundException{
		myComponent=new Component();
		myAttributeFactory = new AttributeFactory();
		AttributeData ad = new AttributeData();
		//TODO clean up code
		Attribute<String> myType = (Attribute<String>) myAttributeFactory.getAttribute(("Type"));
		Attribute<String> projectileImage = (Attribute<String>) myAttributeFactory.getAttribute(("ImageFile"));
		Attribute<String> projectileType = (Attribute<String>) myAttributeFactory.getAttribute(("FireType"));
		Attribute<Integer> projectileHealth = (Attribute<Integer>) myAttributeFactory.getAttribute(("Health"));
		Attribute<Double> projectileDamage = (Attribute<Double>) myAttributeFactory.getAttribute(("FireDamage"));
		Attribute<Double> explosionSize = (Attribute<Double>) myAttributeFactory.getAttribute(("ExplosionRadius"));
		Attribute<Double> slowFactor = (Attribute<Double>) myAttributeFactory.getAttribute(("SlowFactor"));
		Attribute<Double> fireRate = (Attribute<Double>) myAttributeFactory.getAttribute(("FireRate"));

		myType.setValue("Projectile"); //surprisingly, all projectiles are of type Projectile!
		projectileImage.setValue((String) tower.getAttribute("FireImage").getValue());
		projectileType.setValue((String) tower.getAttribute("FireType").getValue());
		projectileHealth.setValue((Integer) tower.getAttribute("Health").getValue());
		projectileDamage.setValue((Double) tower.getAttribute("FireDamage").getValue());
		explosionSize.setValue((Double) tower.getAttribute("ExplosionRadius").getValue());
		slowFactor.setValue((Double) tower.getAttribute("SlowFactor").getValue());
		fireRate.setValue((Double) tower.getAttribute("FireRate").getValue());

		myComponent.setAttributeValue(("Type"), (String) myType.getValue());
		myComponent.setAttributeValue(("ImageFile"), (String) projectileImage.getValue());
		myComponent.setAttributeValue(("FireType"), (String) projectileType.getValue());
		myComponent.setAttributeValue(("Health"), (Integer) projectileHealth.getValue());
		myComponent.setAttributeValue(("FireDamage"), (Double) projectileDamage.getValue());
		myComponent.setAttributeValue(("ExplosionRadius"), (Double) explosionSize.getValue());
		myComponent.setAttributeValue(("SlowFactor"), (Double) slowFactor.getValue());
		myComponent.setAttributeValue(("FireRate"), (Double) fireRate.getValue());
		
		myAttributeData = ad;
	}

	public AttributeData getMyAttributeData() {
		return myAttributeData;
	}
	
	public Component getProjectile(){
		return myComponent;
	}
}
