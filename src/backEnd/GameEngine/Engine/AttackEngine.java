package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;

/**
 * Deals with firing projectiles from towers at targets
 * @author Christian Martindale
 * @author Daniel
 */

public class AttackEngine implements Engine {

	private double masterTime;
	private GameData myGameData;
	private ProjectileFactory myProjectileFactory;
	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		myGameData=gameData;
		masterTime = gameData.getGameTime();
		try {
			myProjectileFactory = new ProjectileFactory();
		} catch (FileNotFoundException e) {
			ErrorDialog fnf = new ErrorDialog();
			fnf.create("Error", "File Not Found");
		}
		
		ComponentGraph myComponentGraph = gameData.getState().getComponentGraph();
		Map<Component, Component> attackersAndTargets=new HashMap<Component, Component>();
		
		for (Component attacker : myComponentGraph.getAllComponents()) {
			if (attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromValueNames("TowerType"))) {
				if (masterTime % ((Double) attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("FireRate")).getValue()/100) <= stepTime) { 
					List<Component> targets = myComponentGraph.getComponentsWithinRadius(attacker,
							(double) attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("FireRadius")).getValue());
					for (Component potentialTarget : targets) {
						if (potentialTarget.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromValueNames("EnemyType"))) {
							attackersAndTargets.put(attacker, potentialTarget);
						}
					}
				}
			}
		}
		for (Component c: attackersAndTargets.keySet()){
			addProjectileToState(c, attackersAndTargets.get(c));
		}
	}

	/**
	 * Creates a projectile, sets its target, and adds it to ComponentGraph in State
	 * @param attacker the tower Component generating a projectile
	 * @param target the enemy Component the projectile will be fired at
	 */
	@SuppressWarnings("unchecked")
	public void addProjectileToState(Component attacker, Component target) {
		Component projectile;
		try {
			projectile = makeProjectile(attacker);
		} catch (FileNotFoundException e1) {
			ErrorDialog fnf = new ErrorDialog();
			fnf.create("Error", "File not found");
			return;
		}

		Object bulletPosObj=attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
		Point2D bulletPos = (Point2D) bulletPosObj;
		Object targetPosObj=target.getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
		Point2D targetPos = (Point2D) targetPosObj;
		
		projectile.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Position"), bulletPos);
		projectile.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("ProjectileTargetPosition"), targetPos);
		projectile.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("ProjectileTarget"), target);
		Point2D finalTargetPoint = targetPos.subtract(bulletPos);
		projectile.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("ProjectileMaxDistance"), finalTargetPoint.magnitude());

		myGameData.getState().getComponentGraph().addComponentToGrid(projectile, bulletPos);
	}

	/**
	 * @return a Component that represents a projectile
	 * @throws FileNotFoundException if the selected image files are not found
	 */
	private Component makeProjectile(Component attacker) throws FileNotFoundException {
		Component projectile = myProjectileFactory.getProjectile(attacker);
		return projectile;
	}
}

