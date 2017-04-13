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
		for (Component componentAttacker : myComponentGraph.getComponentList()) {
			if (componentAttacker.getMyType().equals("TOWER")) {
				for (Component componentTarget : myComponentGraph.getComponentsWithinRadius(componentAttacker,
						Constants.defaultRadius)) {
					addProjectileToState(currentState, componentAttacker, componentTarget);
					componentAttacker.getBehavior("Attack").execute(null);
					componentTarget.getBehavior("TakeDamage").execute(null);
					break;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void addProjectileToState(State currentState, Component attacker, Component target) { 
		Component bullet = makeBullet();

		currentState.getComponentGraph().addComponentToGrid(bullet,
				(Point2D) attacker.getAttribute("Position").getValue());
	}
	
	/**
	 * 
	 * @return a Component that represents a Bullet
	 */
	@SuppressWarnings("unchecked")
	private Component makeBullet(){//TODO: ADD ATTRIBUTES STARTPOS AND TARGETPOS TO BULLET FOR MOVEENGINE TO USE

		AttributeFactory af = null;
		Component bullet = null;
		try {
			af = new AttributeFactory();
		} 
		catch (FileNotFoundException e) {
			ErrorDialog fnf = new ErrorDialog();
			fnf.create("Error", "File not found");
		}

		 

			bullet.setMyType("Projectile");
			Attribute<String> bulletImage = (Attribute<String>) af.getAttribute(myResources.getString("ImageFile"));
			bulletImage.setValue(Constants.BULLET_IMAGE_FILE);
			AttributeData ad = new AttributeData();
			ad.addAttribute(myResources.getString("ImageFile"), (backEnd.Attribute.AttributeImpl<?>) bulletImage);

		return bullet;
	}

}
