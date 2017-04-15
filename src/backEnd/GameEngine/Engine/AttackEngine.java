package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactory;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import frontEnd.Menus.ErrorDialog;
import javafx.geometry.Point2D;
import resources.Constants;

/**
 * 
 * @author Daniel
 * @author Alex
 * @author Christian
 */

public class AttackEngine implements Engine {
	private final static String RESOURCES_PATH = "resources/attributes";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);

	@Override
	public void gameLoop(State currentState, double stepTime) {
		ComponentGraph myComponentGraph = currentState.getComponentGraph();
		for (Component componentAttacker : myComponentGraph.getAllComponents()) {
			if (componentAttacker.getMyType().equals("TOWER")) {
				for (Component componentTarget : myComponentGraph.getComponentsWithinRadius(componentAttacker,
						Constants.defaultRadius)) {
					addProjectileToState(currentState, componentAttacker, componentTarget);
					try {
						componentAttacker.getBehavior("Attack").execute(null);
					} catch (FileNotFoundException e) {
						ErrorDialog fnf = new ErrorDialog();
						fnf.create("Error", "File not found");
					}
					try {
						componentTarget.getBehavior("TakeDamage").execute(null);
					} catch (FileNotFoundException e) {
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
		Component bullet = null;
		try {
			bullet = makeBullet();
		} catch (FileNotFoundException e) {
			ErrorDialog fnf = new ErrorDialog();
			fnf.create("Error", "File not found");
		}

		Point2D bulletPos = (Point2D) attacker.getAttribute(myResources.getString("Position"));
		Point2D targetPos = (Point2D) target.getAttribute(myResources.getString("Position"));
		
		bullet.setAttributeValue(myResources.getString("Position"), bulletPos);
		bullet.setAttributeValue(myResources.getString("ProjectileTargetPosition"), targetPos);
		bullet.setAttributeValue(myResources.getString("ProjectileDistance"), targetPos.subtract(bulletPos));
		currentState.getComponentGraph().addComponentToGrid(bullet, bulletPos);
	}

	/**
	 * 
	 * @return a Component that represents a Bullet
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private Component makeBullet() throws FileNotFoundException {

		AttributeFactory af = new AttributeFactory();
		AttributeData ad = new AttributeData();
		Component bullet = new Component(ad);

		bullet.setMyType("Projectile");				
		Attribute<String> bulletImage = (Attribute<String>) af.getAttribute(myResources.getString("ImageFile"));
		bulletImage.setValue(Constants.BULLET_IMAGE_FILE);
		ad.addAttribute(myResources.getString("ImageFile"), (backEnd.Attribute.AttributeImpl<?>) bulletImage);
		
		return bullet;
	}

}
