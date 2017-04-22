package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;
import java.util.List;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactory;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;

/**
 * Deals with firing projectiles from towers at targets
 * @author Christian
 */

public class AttackEngine implements Engine {

	private double masterTime;

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		masterTime = gameData.getGameTime();

		ComponentGraph myComponentGraph = gameData.getState().getComponentGraph();
		for (Component attacker : myComponentGraph.getAllComponents()) {
			if (attacker.getAttribute("Type").getValue().equals("Tower")) {
				if ( masterTime % ((Double) attacker.getAttribute("FireRate").getValue()) <= stepTime) {
					List<Component> targets = myComponentGraph.getComponentsWithinRadius(attacker,
							(double) attacker.getAttribute("FireRadius").getValue());
					addProjectileToState(gameData.getState(), attacker, targets.get(targets.size() - 1)); // we
																											// want
																											// the
																											// furthest
																											// target
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

		projectile.setAttributeValue("Position", bulletPos);
		projectile.setAttributeValue("ProjectileTargetPosition", targetPos);
		projectile.setAttributeValue("ProjectileDistance", targetPos.subtract(bulletPos));
		projectile.setAttributeValue("ProjectileTarget", target);

		currentState.getComponentGraph().addComponentToGrid(projectile, bulletPos);
	}

	/**
	 * 
	 * @return a Component that represents a projectile
	 * @throws FileNotFoundException if the selected image files are not found
	 * 
	 */
	@SuppressWarnings("unchecked")
	private Component makeProjectile(Component attacker) throws FileNotFoundException {
		ProjectileFactory bulletFactory = new ProjectileFactory(attacker);
		Component projectile = new Component(bulletFactory.getMyAttributeData());
		projectile.setMyType("Projectile");
		return projectile;
	}
}
