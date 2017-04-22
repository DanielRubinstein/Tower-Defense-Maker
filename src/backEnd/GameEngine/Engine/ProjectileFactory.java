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
	
	@SuppressWarnings("unchecked")
	public ProjectileFactory(Component tower) throws FileNotFoundException{
		
		myAttributeFactory = new AttributeFactory();
		AttributeData ad = new AttributeData();

		Attribute<String> myType = (Attribute<String>) myAttributeFactory.getAttribute(("Type"));
		Attribute<String> projectileImage = (Attribute<String>) myAttributeFactory.getAttribute(("ImageFile"));
		Attribute<String> projectileType = (Attribute<String>) myAttributeFactory.getAttribute(("FireType"));
		Attribute<Integer> projectileDamage = (Attribute<Integer>) myAttributeFactory.getAttribute(("FireDamage"));
		Attribute<Double> explosionSize = (Attribute<Double>) myAttributeFactory.getAttribute(("ExplosionRadius"));
		Attribute<Double> slowFactor = (Attribute<Double>) myAttributeFactory.getAttribute(("SlowFactor"));
		Attribute<Double> fireRate = (Attribute<Double>) myAttributeFactory.getAttribute(("FireRate"));

		myType.setValue((String) tower.getAttribute("Projectile").getValue());
		projectileImage.setValue((String) tower.getAttribute("FireImage").getValue());
		projectileType.setValue((String) tower.getAttribute("FireType").getValue());
		projectileDamage.setValue((Integer) tower.getAttribute("FireDamage").getValue());
		explosionSize.setValue((Double) tower.getAttribute("ExplosionRadius").getValue());
		slowFactor.setValue((Double) tower.getAttribute("SlowFactor").getValue());
		fireRate.setValue((Double) tower.getAttribute("FireRate").getValue());

		ad.addAttribute(("Type"), (backEnd.Attribute.AttributeImpl<?>) myType);
		ad.addAttribute(("ImageFile"), (backEnd.Attribute.AttributeImpl<?>) projectileImage);
		ad.addAttribute(("FireType"), (backEnd.Attribute.AttributeImpl<?>) projectileType);
		ad.addAttribute(("FireDamage"), (backEnd.Attribute.AttributeImpl<?>) projectileDamage);
		ad.addAttribute(("ExplosionRadius"), (backEnd.Attribute.AttributeImpl<?>) explosionSize);
		ad.addAttribute(("SlowFactor"), (backEnd.Attribute.AttributeImpl<?>) slowFactor);
		ad.addAttribute(("FireRate"), (backEnd.Attribute.AttributeImpl<?>) fireRate);
		
		myAttributeData = ad;
	}

	public AttributeData getMyAttributeData() {
		return myAttributeData;
	}
}
