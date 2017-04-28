package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactoryImpl;
import backEnd.Attribute.AttributeFactoryReader;
import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.Component;

/**
 * 
 * @author Christian Martindale
 * creates projectiles using the data from their generating towers
 * for use in AttackEngine
 */
public class ProjectileFactory {

	private Component myComponent;
	
	@SuppressWarnings("unchecked")
	public ProjectileFactory(Component tower) throws FileNotFoundException{
		myComponent=new Component();
		
		myComponent.setAttributeValue("Type", "Projectile");
		myComponent.setAttributeValue("ImageFile", (String) tower.getAttribute("FireImage").getValue());
		myComponent.setAttributeValue("FireType", (String) tower.getAttribute("FireType").getValue());
		myComponent.setAttributeValue("FireDamage", (Integer)tower.getAttribute("FireDamage").getValue());
		myComponent.setAttributeValue("Velocity", (Double)tower.getAttribute("Velocity").getValue());
		myComponent.setAttributeValue("ProjectileMaxDistance", 0.0);
		myComponent.setAttributeValue("ProjectileTraveled", 0.0);
		myComponent.setAttributeValue("ExplosionRadius", (Double)tower.getAttribute("ExplosionRadius").getValue());
		myComponent.setAttributeValue("SlowFactor", (Double)tower.getAttribute("SlowFactor").getValue());
		myComponent.setAttributeValue("FireRate", (Double)tower.getAttribute("FireRate").getValue());
		myComponent.setAttributeValue("ProjectileTarget", (Component)tower.getAttribute("ProjectileTarget").getValue());	
	}

	public Component getProjectile(){
		return myComponent;
	}
}
