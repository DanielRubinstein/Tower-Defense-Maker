package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactory;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;
import resources.Constants;

/**
 * 
 * @author Daniel
 * @author Alex
 * @author Christian
 */

public class AttackEngine implements Engine {
	//private final static String RESOURCES_PATH = "resources/allAttributeTypes";
	//private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);

	@Override
	public void gameLoop(State currentState, double stepTime) {
		ComponentGraph myComponentGraph = currentState.getComponentGraph();
		for (Component attacker : myComponentGraph.getAllComponents()) {
			if (attacker.getMyType().equals("TOWER")) {
				for (Component componentTarget : myComponentGraph.getComponentsWithinRadius(attacker,
						(double) attacker.getAttribute("FireRadius").getValue())) {
					addProjectileToState(currentState, attacker, componentTarget);
					try {
						attacker.getBehavior("Attack").execute(null);
					} 
					catch (FileNotFoundException e) {
						ErrorDialog fnf = new ErrorDialog();
						fnf.create("Error", "File not found");
					}
					//needs to be refactored - only works for single target attacks
					 
					try {
						componentTarget.getBehavior("TakeDamage").execute(attacker);
					} 
					catch (FileNotFoundException e) {
						ErrorDialog fnf = new ErrorDialog();
						fnf.create("Error", "File not found");
					}
					break;
					
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void addProjectileToState(State currentState, Component attacker, Component target) {
		Component projectile = null;
		try {
				projectile = makeProjectile(attacker);
			
		} catch (FileNotFoundException e) {
			ErrorDialog fnf = new ErrorDialog();
			fnf.create("Error", "File not found");
		}

		Point2D bulletPos = (Point2D) attacker.getAttribute(("Position"));
		Point2D targetPos = (Point2D) target.getAttribute(("Position"));
		
		projectile.setAttributeValue(("Position"), bulletPos);
		projectile.setAttributeValue(("ProjectileTargetPosition"), targetPos);
		projectile.setAttributeValue(("ProjectileDistance"), targetPos.subtract(bulletPos));
		
		currentState.getComponentGraph().addComponentToGrid(projectile, bulletPos);
	}


	/**
	 * 
	 * @return a Component that represents a projectile
	 * @throws FileNotFoundException if the image files are not found
	 * 
	 * This class needs to be refactored - I don't understand how attributefactory really works
	 * is there an easier/better way to make the attributes instead of manually?
	 * 
	 */
	@SuppressWarnings("unchecked")
	private Component makeProjectile(Component attacker) throws FileNotFoundException {

		AttributeFactory af = new AttributeFactory();
		AttributeData ad = new AttributeData();
	
		Attribute<String> projectileImage = (Attribute<String>) af.getAttribute(("ImageFile"));
		Attribute<String> projectileType = (Attribute<String>) af.getAttribute(("FireType"));
		Attribute<Integer> projectileDamage = (Attribute<Integer>) af.getAttribute(("FireDamage"));
		Attribute<Double> explosionSize = (Attribute<Double>) af.getAttribute(("ExplosionRadius"));
		
		projectileImage.setValue((String) attacker.getAttribute("FireImage").getValue());
		projectileType.setValue((String) attacker.getAttribute("FireType").getValue());
		projectileDamage.setValue((Integer) attacker.getAttribute("FireDamage").getValue());
		explosionSize.setValue((Double) attacker.getAttribute("ExplosionRadius").getValue());
		
		ad.addAttribute(("ImageFile"), (backEnd.Attribute.AttributeImpl<?>) projectileImage);
		ad.addAttribute(("FireType"), (backEnd.Attribute.AttributeImpl<?>) projectileType);
		ad.addAttribute(("FireDamage"), (backEnd.Attribute.AttributeImpl<?>) projectileDamage);
		ad.addAttribute(("ExplosionRadius"), (backEnd.Attribute.AttributeImpl<?>) explosionSize);
		
		Component projectile = new Component(ad);
		projectile.setMyType("Projectile");	
		return projectile;
	}
}
